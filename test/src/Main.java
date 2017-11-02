import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    
    public static void main(String[] args){
        // 填写外网ip, 百度一下ip就能看到了121.32.150.236
//        final byte[] ip = {112, (byte) 65, (byte) 142, 74};
        final byte[] ip = {121,(byte)32,(byte)150,(byte)236};
        // 如果想收到其他节点发送过来的消息必须把外网端口映射到内网端口 这里是内网端口 只要登录路由器做一个端口映射就好
        final int port = 50000;
        // 节点数 这里表示从50000到50255这256个端口将会被监听
        final int nodeCount = 256;
        final int processorCount = Runtime.getRuntime().availableProcessors();
        final String id = "javadht:541241544";
        
        List<byte[]> ips = new ArrayList<byte[]>();
        List<Integer> ports = new ArrayList<Integer>();
        List<String> ids = new ArrayList<String>();
        for(int i = 0; i < nodeCount; i++) {
            if(i % (nodeCount / processorCount) == 0 && ips.size() != 0) {
                new DHTServer(ips, ports, ids);
                ips = new ArrayList();
                ports = new ArrayList();
                ids = new ArrayList();
            }
            ips.add(ip);
            ports.add(port + i);
            ids.add((i + 740) + id);
        }
        if(ips.size() != 0) {
            new DHTServer(ips, ports, ids);
        }
    }
}

interface BencodeType {
    int getLength();
    int getTotalLength();
    byte[] getData();
    byte[] getTotalData();
}

class BencodeString implements BencodeType, Comparable<BencodeString> {
    
    private final String content;
    
    public BencodeString(String content) {
        this.content = content;
    }
    
    public BencodeString(byte[] bs) {
        try {
            this.content = new String(bs, "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static BencodeString getString(String source, int index) {
        char c = source.charAt(index);
        if(c >= '1' && c <= '9') {
            source = source.substring(index);
            String lengthStr = source.split(":")[0];
            int length = Integer.parseInt(lengthStr);
            return new BencodeString(source.substring(lengthStr.length() + 1, lengthStr.length() + 1 + length));
            
        }
        return null;
    }

    public int getLength() {
        return content.length();
    }
    
    public int getTotalLength() {
        return getLength() + String.valueOf(getLength()).length() + 1;
    }

    public byte[] getData() {
        return getData("iso-8859-1");
    }
    
    public byte[] getData(String charsetName) {
        try {
            return content.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] getTotalData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(String.valueOf(getLength()).getBytes("iso-8859-1"));
            baos.write((byte)':');
            baos.write(getData());
        } catch (IOException e) {
        }
        return baos.toByteArray();
    }

    public String toString() {
        return "\"" + content + "\"";
    }

    public int compareTo(BencodeString o) {
        return this.content.compareTo(o.content);
    }
}

class BencodeInteger implements BencodeType {
    
    private final String content;
    
    public BencodeInteger(String content) {
        this.content = content;
    }
    
    public static BencodeInteger getInt(String source, int index) {
        char c = source.charAt(index);
        if(c == 'i') {
            source = source.substring(index + 1);
            return new BencodeInteger(source.substring(0, source.indexOf("e")));
        }
        return null;
    }

    public int getLength() {
        return content.length();
    }
    
    public int getTotalLength() {
        return getLength() + 2;
    }

    public byte[] getData() {
        try {
            return content.getBytes("iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] getTotalData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write((byte)'i');
            baos.write(getData());
            baos.write((byte)'e');
        } catch (IOException e) {
        }
        return baos.toByteArray();
    }

    public String toString() {
        return content;
    }
}

@SuppressWarnings("serial")
class BencodeList extends ArrayList<BencodeType> implements BencodeType {
    
    public static BencodeList getList(String source, int index) {
        char c = source.charAt(index++);
        if(c == 'l') {
            BencodeList result = new BencodeList();
            for(;;) {
                int temp = index;
                index += result.addElement(BencodeString.getString(source, index));
                index += result.addElement(BencodeInteger.getInt(source, index));
                index += result.addElement(BencodeList.getList(source, index));
                index += result.addElement(BencodeMap.getMap(source, index));
                if(index != temp) {
                    continue;
                }
                if(source.charAt(index) == 'e') {
                    break;
                } else {
                    throw new RuntimeException();
                }
            }
            return result;
        }
        return null;
    }
    
    public int getLength() {
        int length = 0;
        for(BencodeType element : this) {
            length += element.getTotalLength();
        }
        return length;
    }
    
    public int getTotalLength() {
        return getLength() + 2;
    }

    public byte[] getData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            for(BencodeType element : this) {
                baos.write(element.getTotalData());
            }
        } catch (Exception e) {
        }
        return baos.toByteArray();
    }
    
    public byte[] getTotalData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write((byte)'l');
            baos.write(getData());
            baos.write((byte)'e');
        } catch (IOException e) {
        }
        return baos.toByteArray();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(BencodeType element : this) {
            sb.append(", ");
            sb.append(element.toString());
        }
        return sb.length() > 0 ? "[" + sb.toString().substring(2) + "]" : "[]";
    }
    
    private int addElement(BencodeType element) {
        if(null != element) {
            add(element);
            return element.getTotalLength();
        }
        return 0;
    }
}

@SuppressWarnings("serial")
class BencodeMap extends TreeMap<BencodeString, BencodeType> implements BencodeType {
    
    public static BencodeMap getMap(String source, int index) {
        char c = source.charAt(index++);
        if(c == 'd') {
            BencodeMap result = new BencodeMap();
            BencodeString key = null;
            for(;;) {
                BencodeType element;
                if(null != (element = BencodeString.getString(source, index)) || 
                        null != (element = BencodeInteger.getInt(source, index)) || 
                        null != (element = BencodeList.getList(source, index)) || 
                        null != (element = BencodeMap.getMap(source, index))) {
                    if(null != key) {
                        result.put(key, element);
                        key = null;
                    } else {
                        key = (BencodeString) element;
                    }
                    index += element.getTotalLength();
                    continue;
                }
                if(source.charAt(index) == 'e') {
                    break;
                } else {
                    throw new RuntimeException();
                }
            }
            return result;
        }
        return null;
    }

    public int getLength() {
        int length = 0;
        for(Map.Entry<BencodeString, BencodeType> entry : entrySet()) {
            length += entry.getKey().getTotalLength();
            length += entry.getValue().getTotalLength();
        }
        return length;
    }
    
    public int getTotalLength() {
        return getLength() + 2;
    }

    public byte[] getData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            for(Map.Entry<BencodeString, BencodeType> entry : entrySet()) {
                baos.write(entry.getKey().getTotalData());
                baos.write(entry.getValue().getTotalData());
            }
        } catch (Exception e) {
        }
        return baos.toByteArray();
    }
    
    public byte[] getTotalData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write((byte)'d');
            baos.write(getData());
            baos.write((byte)'e');
        } catch (IOException e) {
        }
        return baos.toByteArray();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<BencodeString, BencodeType> entry : entrySet()) {
            sb.append(", ");
            sb.append(entry.getKey().toString());
            sb.append(" : ");
            sb.append(entry.getValue().toString());
        }
        return sb.length() > 0 ? "{" + sb.toString().substring(2) + "}" : "{}";
    }
}

class BucketList {
    
    private final List<Bucket> buckets = new ArrayList<Bucket>();
    private final Node currentNode;
    
    public BucketList(byte[] info) {
        currentNode = new Node(info);
        try {
            Bucket b = new Bucket(new BigInteger("0"), new BigInteger(Bucket.MAX_VALUE.getBytes("iso-8859-1")));
            b.addNode(currentNode);
            buckets.add(b);
            Bucket nb;
            while(null != (nb = b.split())) {
                buckets.add(nb);
            }
            Collections.sort(buckets);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public synchronized List<Node> getNearestNodes(NodeId id) {
        int index = 0;
        for(int i = 0; i < buckets.size(); i++) {
            if(buckets.get(i).contain(id.getValue())) {
                index = i;
            }
        }
        
        List<Node> nodes = new ArrayList<Node>(buckets.get(index).getNodes());
        List<Node> leftNodes = new ArrayList<Node>();
        List<Node> rightNodes = new ArrayList<Node>();
        for(int i = index - 1; i >= 0; i--) {
            leftNodes.addAll(buckets.get(i).getNodes());
            if(leftNodes.size() >= 8) {
                break;
            }
        }
        for(int i = index + 1; i < buckets.size(); i++) {
            rightNodes.addAll(buckets.get(i).getNodes());
            if(rightNodes.size() >= 8) {
                break;
            }
        }
        
        nodes.addAll(leftNodes);
        nodes.addAll(rightNodes);
        
        Set<BigInteger> distances = new TreeSet<BigInteger>();
        Map<BigInteger, List<Node>> distance2Node = new HashMap<BigInteger, List<Node>>();
        for(Node n : nodes) {
            BigInteger distance = n.getId().getValue().subtract(id.getValue()).abs();
            distances.add(distance);
            List<Node> value = distance2Node.get(distance);
            if(null == value) {
                value = new ArrayList<Node>();
                distance2Node.put(distance, value);
            }
            value.add(n);
        }
        List<Node> result = new ArrayList<Node>();
        for(BigInteger distance : distances) {
            result.addAll(distance2Node.get(distance));
            if(result.size() >= 8) {
                break;
            }
        }
        
        return result.size() > 8 ? result.subList(0, 8) : result;
    }

    public synchronized boolean addNode(Node node) {
        for(Bucket bucket : buckets) {
            if(bucket.addNode(node)) {
                return true;
            }
        }
        
        return false;
    }
    
    public Node getCurrentNode() {
        return currentNode;
    }
    
    public List<Bucket> getBuckets() {
        return buckets;
    }
}

class Bucket implements Comparable<Bucket> {
    private BigInteger start;
    private BigInteger end;
    private Set<Node> nodes = new HashSet<Node>(8);
    
    public static final String MAX_VALUE = ((char) 0) + "ÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿÿ";
    
    public Bucket(BigInteger start, BigInteger end) {
        this.start = start;
        this.end = end;
    }
    
    public boolean contain(BigInteger bi) {
        return start.compareTo(bi) <= 0 && end.compareTo(bi) >= 0;
    }
    
    public boolean addNode(Node node) {
        return nodes.size() < 8 && contain(node.getId().getValue()) ? nodes.add(node) : false;
    }
    
    public Set<Node> getNodes() {
        return nodes;
    }
    
    Bucket split() {
        BigInteger len = end.subtract(start).add(new BigInteger("1"));
        if(len.compareTo(new BigInteger("8")) == 0) {
            return null;
        }
        BigInteger newStart = start.add(len.divide(new BigInteger("2")));
        BigInteger newEnd = newStart.subtract(new BigInteger("1"));
        
        Bucket b1 = new Bucket(start, newEnd);
        Bucket b2 = new Bucket(newStart, end);
        
        Node currentNode = (Node) nodes.toArray()[0];
        if(b1.contain(currentNode.getId().getValue())) {
            end = newEnd;
            return b2;
        }
        
        start = newStart;
        return b1;
    }

    public int compareTo(Bucket o) {
        return start.compareTo(o.start);
    }
}

class Node {
    private final NodeId id;
    private final byte[] ip;
    private final byte[] port;
    
    public Node(String ip, int port, String nodeId) {
        String[] ss = ip.split("\\.");
        this.ip = new byte[] {(byte) Integer.parseInt(ss[0]), (byte) Integer.parseInt(ss[1]), (byte) Integer.parseInt(ss[2]), (byte) Integer.parseInt(ss[3])};
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(port);
        bb.flip();
        this.port = Arrays.copyOfRange(bb.array(), 2, 4);
        try {
            id = new NodeId(nodeId.getBytes("iso-8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }
    
    public Node(byte[] bs) {
        if(bs.length != 26) {
            throw new RuntimeException();
        }
        id = new NodeId(Arrays.copyOfRange(bs, 0, 20));
        ip = Arrays.copyOfRange(bs, 20, 24);
        port = Arrays.copyOfRange(bs, 24, 26);
    }
    
    public NodeId getId() {
        return id;
    }
    
    public byte[] getIp() {
        return ip;
    }
    
    public byte[] getPort() {
        return port;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Node) {
            Node n = (Node) obj;
            return id.getValue().equals(n.getId().getValue());
        }
        return false;
    }
}

class NodeId extends BencodeString {
    
    private final BigInteger value;
    
    public NodeId(byte[] bs) {
        super(bs);
        if(bs.length != 20) {
            throw new RuntimeException();
        }
        
        byte[] dest = new byte[bs.length + 1];
        dest[0] = 0;
        System.arraycopy(bs, 0, dest, 1, bs.length);
        value = new BigInteger(dest);
    }
    
    public BigInteger getValue() {
        return value;
    }
}

class DHTServer {

    private final Selector selector;
    private final ExecutorService udpReader = Executors.newSingleThreadExecutor();
    private final ExecutorService udpWriter = Executors.newSingleThreadExecutor();
    private final ExecutorService dataProcessor = Executors.newSingleThreadExecutor();
    private final BlockingQueue<Object[]> responseDataQueue = new LinkedBlockingQueue<Object[]>();
    private final BlockingQueue<Object[]> requestDataQueue = new LinkedBlockingQueue<Object[]>();
    private final Lock lock = new ReentrantLock();
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
    
    private static final ExecutorService WORKER = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final ExecutorService NODE_FINDER = Executors.newSingleThreadExecutor();
    private static final CopyOnWriteArrayList<Object[]> NODES = new CopyOnWriteArrayList<Object[]>();
    public static final String[][] ROOT_NODES = {
            {"router.utorrent.com", "6881", null},
            {"dht.transmissionbt.com", "6881", null}, 
            {"router.bittorrent.com", "6881", null}
    };
    
    
    public DHTServer(List<byte[]> ips, List<Integer> ports, List<String> ids) {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        for(int i = 0; i < ips.size(); i++) {
            bind(ips.get(i), ports.get(i), ids.get(i));
        }

        udpReader.execute(new Runnable() {
            public void run() {
                for(;;) {
                    try {
                        if (selector.select() == 0) {
                            continue;
                        }
    
                        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                        while (it.hasNext()) {
                            final SelectionKey key = it.next();
                            final DatagramChannel dc = (DatagramChannel) key.channel();
    
                            if (key.isReadable()) {
                                SocketAddress address = dc.receive(buffer);
                                buffer.flip();
                                byte[] result = new byte[buffer.limit()];
                                buffer.get(result);
                                buffer.clear();
                                
                                if(result.length > 0) {
                                    responseDataQueue.put(new Object[]{key, result, address});
                                }
                            }
    
                            it.remove();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        
        udpWriter.execute(new Runnable() {
            public void run() {
                for(;;) {
                    try {
                        Object[] info = requestDataQueue.take();
                        ByteBuffer data = (ByteBuffer) info[0];
                        SocketAddress target = (SocketAddress) info[1];
                        final DatagramChannel dc = (DatagramChannel) ((SelectionKey) info[2]).channel();
                        
                        while(data.hasRemaining()) {
                            dc.send(data, target);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        
        dataProcessor.execute(new Runnable() {
            public void run() {
                for(;;) {
                    SelectionKey key = null;
                    byte[] data = null;
                    InetSocketAddress address = null;
                    try {
                        Object[] element = responseDataQueue.take();
                        key = (SelectionKey) element[0];
                        data = (byte[]) element[1];
                        address = (InetSocketAddress) element[2];
                        BucketList bucketList = (BucketList) ((Object[]) key.attachment())[2];
                        
                        final BencodeMap response = BencodeMap.getMap(new String(data, "iso-8859-1"), 0);
                        
                        String y = new String(response.get(new BencodeString("y")).getData(), "iso-8859-1");
                        if(y.equals("r")) {
                            final Callbacker cb = getCallbacker(key, ByteBuffer.wrap(response.get(new BencodeString("t")).getData()).getChar() - 0);
                            if(null != cb) {
                                WORKER.execute(new Runnable() {
                                    public void run() {
                                        cb.execute(response);
                                    }
                                });
                            }
                        } else if(y.equals("q")) {
                            String id = new String(((BencodeMap) (response.get(new BencodeString("a")))).get(new BencodeString("id")).getData(), "iso-8859-1");
                            String t = new String(response.get(new BencodeString("t")).getData(), "iso-8859-1");
                            bucketList.addNode(new Node(address.getAddress().getHostAddress(), address.getPort(), id));
                            
                            String q = new String(response.get(new BencodeString("q")).getData(), "iso-8859-1");
                            if(q.equals("ping")) {
                                responsePing(key, address, t);
                            } else if(q.equals("find_node")) {
                                String target = new String(((BencodeMap) (response.get(new BencodeString("a")))).get(new BencodeString("target")).getData(), "iso-8859-1");
                                
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                for(Node node : bucketList.getNearestNodes(new NodeId(target.getBytes("iso-8859-1")))) {
                                    baos.write(node.getId().getData());
                                    baos.write(node.getIp());
                                    baos.write(node.getPort());
                                }
                                responseFindNode(key, address, t, new String(baos.toByteArray(), "iso-8859-1"));
                            } else if(q.equals("get_peers")) {
                                String infoHash = new String(((BencodeMap) (response.get(new BencodeString("a")))).get(new BencodeString("info_hash")).getData(), "iso-8859-1");
                                println(infoHash);
                                
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                for(Node node : bucketList.getNearestNodes(new NodeId(infoHash.getBytes("iso-8859-1")))) {
                                    baos.write(node.getId().getData());
                                    baos.write(node.getIp());
                                    baos.write(node.getPort());
                                }
                                responseGetPeers(key, address, t, new String(baos.toByteArray(), "iso-8859-1"), "dg");
                            } else if(q.equals("announce_peer")) {
                                String infoHash = new String(((BencodeMap) (response.get(new BencodeString("a")))).get(new BencodeString("info_hash")).getData(), "iso-8859-1");
                                println(infoHash);
                                
                                responseAnnouncePeer(key, address, t);
                            }
                        }
                        
                    } catch (Exception e) {
                    }
                }
            }
            
            private void println(String infoHash) {
                StringBuilder msg = new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())).append(" magnet:?xt=urn:btih:");
                for(char c : infoHash.toCharArray()) {
                    String hs = Integer.toHexString(c);
                    if(hs.length() == 1) {
                        msg.append(0);
                    }
                    msg.append(hs);
                }
                System.out.println(msg);
            }
        });
        
        NODE_FINDER.execute(new Runnable() {
            
            private static final int TPS = 10;
            private static final int MAX_STACK = 10000;
            private static final int MAX_OLD_NODE = 10000;
            
            @SuppressWarnings("unchecked")
            public void run() {
                for(;;) {
                    for(Object[] objs : NODES) {
                        final Stack<String[]> stack = (Stack<String[]>) objs[0];
                        final Set<String> oldNode = (Set<String>) objs[1];
                        final SelectionKey key = (SelectionKey) objs[2];
                        final BucketList bucketList = (BucketList) objs[3];
                        final String id = (String) objs[4];

                        String[] temp = null;
                        boolean isOver = false;
                        synchronized (stack) {
                            if(stack.size() > 0) {
                                if(stack.size() > MAX_STACK) {
                                    for(int i = 0; i < MAX_STACK; i++) {
                                        stack.remove(i);
                                    }
                                }
                                temp = stack.pop();
                            } else {
                                isOver = true;
                            }
                        }
                        
                        if(isOver) {
                            NODES.remove(objs);
                            continue;
                        }
                        
                        final String[] address = temp;
                        final InetSocketAddress target = new InetSocketAddress(address[0], Integer.parseInt(address[1]));

                        synchronized (oldNode) {
                            if(oldNode.contains(address[0] + ":" + address[1])) {
                                continue;
                            } else {
                                if(oldNode.size() > MAX_OLD_NODE) {
                                    for(int i = 0; i < MAX_OLD_NODE / 2; i++) {
                                        oldNode.remove(i);
                                    }
                                }
                                oldNode.add(address[0] + ":" + address[1]);
                            }
                        }
                        
                        ping(key, target, new Callbacker() {
                            public void execute(BencodeMap response) {
                                String ip = address[0];
                                String port = address[1];
                                String nodeId = address[2];
                                
                                if(null != nodeId) {
                                    bucketList.addNode(new Node(ip, Integer.parseInt(port), nodeId));
                                }
                                
                                findNode(key, target, id, new Callbacker() {
                                    public void execute(BencodeMap response) {
                                        try {
                                            byte[] nodes = ((BencodeMap)(response.get(new BencodeString("r")))).get(new BencodeString("nodes")).getData();
                                            
                                            for(int i = 0; i < nodes.length; i += 26) {
                                                String nodeId = new String(Arrays.copyOfRange(nodes, i, i + 20), "iso-8859-1");
                                                byte[] ipPort = Arrays.copyOfRange(nodes, i + 20, i + 26);
                                                String ip = (ipPort[0] & 0xFF) + "." + (ipPort[1] & 0xFF) + "." + (ipPort[2] & 0xFF) + "." + (ipPort[3] & 0xFF);
                                                int port = ByteBuffer.wrap(new byte[]{0, 0, ipPort[4], ipPort[5]}).getInt();
                                                
                                                boolean isContain = true;
                                                synchronized (stack) {
                                                    synchronized (oldNode) {
                                                        if(!oldNode.contains(ip + ":" + port)) {
                                                            isContain = false;
                                                        }
                                                    }
                                                
                                                    if(!isContain) {
                                                        stack.push(new String[]{ip, String.valueOf(port), nodeId});
                                                    }
                                                }
                                            }
                                        }catch (Exception e) {
                                        }
                                    }
                                });
                            }
                        });
                        
                        try {
                            Thread.sleep(1000 / TPS);
                        } catch (InterruptedException e) { }
                    }
                }
            }
        });
    }
    
    private SelectionKey bind(byte[] ip, int port, String id) {
        SelectionKey key = null;
        try {
            List<Integer> transactionIds = new ArrayList<Integer>(Character.MAX_VALUE + 1);
            for(int i = 0; i <= Character.MAX_VALUE; i++) {
                transactionIds.add(i);
            }
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(id.getBytes("iso-8859-1"));
            baos.write(ip);
            ByteBuffer bb = ByteBuffer.allocate(4).putInt(port);
            bb.flip();
            baos.write(Arrays.copyOfRange(bb.array(), 2, 4));
            BucketList bucketList = new BucketList(baos.toByteArray());
            
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port));
            key = channel.register(selector, SelectionKey.OP_READ);
            key.attach(
                    new Object[]{
                            transactionIds,
                            new ArrayList<Callbacker>(Character.MAX_VALUE + 1),
                            bucketList,
                            id
                    }
            );
            
            Stack<String[]> stack = new Stack<String[]>();
            for(String[] address : ROOT_NODES) {
                stack.push(address);
            }
            NODES.add(new Object[]{stack, new HashSet<String>(), key, bucketList, id});
        } catch (Exception e) {
        }
        
        return key;
    }
    
    @SuppressWarnings("unchecked")
    public int addTransaction(SelectionKey key, Callbacker cb) {
        lock.lock();
        try {
            Object[] attachment = (Object[]) key.attachment();
            List<Integer> transactionIds = (List<Integer>) attachment[0];
            List<Callbacker> callbackers = (List<Callbacker>) attachment[1];
            
            if(transactionIds.isEmpty()) {
                removeTransaction(key);
            }
            cb.setTransactionId(transactionIds.remove(0));
            callbackers.add(cb);
        } finally {
            lock.unlock();
        }
        return cb.getTransactionId();
    }

    @SuppressWarnings("unchecked")
    public Callbacker getCallbacker(SelectionKey key, int transactionId) {
        Callbacker result = null;
        lock.lock();
        try {
            Object[] attachment = (Object[]) key.attachment();
            List<Integer> transactionIds = (List<Integer>) attachment[0];
            List<Callbacker> callbackers = (List<Callbacker>) attachment[1];
            
            for(Callbacker cb : callbackers) {
                if(cb.getTransactionId() == transactionId) {
                    result = cb;
                    break;
                }
            }
            if(null != result) {
                callbackers.remove(result);
                transactionIds.add(result.getTransactionId());
            }
        } finally {
            lock.unlock();
        }
        
        return result;
    }

    @SuppressWarnings("unchecked")
    public void removeTransaction(SelectionKey key) {
        lock.lock();
        try {
            Object[] attachment = (Object[]) key.attachment();
            List<Integer> transactionIds = (List<Integer>) attachment[0];
            List<Callbacker> callbackers = (List<Callbacker>) attachment[1];
            
            final Callbacker cb = callbackers.remove(0);
            transactionIds.add(cb.getTransactionId());
            WORKER.execute(new Runnable() {
                public void run() {
                    cb.destory();
                }
            });
        } finally {
            lock.unlock();
        }
    }
    
    public void ping(SelectionKey key, InetSocketAddress target, Callbacker cb) {
        try {
            ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) addTransaction(key, cb));
            bb.flip();
            
            BencodeMap request = new BencodeMap();
            request.put(new BencodeString("t"), new BencodeString(new String(bb.array(), "iso-8859-1")));
            request.put(new BencodeString("y"), new BencodeString("q"));
            request.put(new BencodeString("q"), new BencodeString("ping"));
            
            BencodeMap params = new BencodeMap();
            params.put(new BencodeString("id"), new BencodeString((String) ((Object[]) key.attachment())[3]));
            request.put(new BencodeString("a"), params);
            
            requestDataQueue.put(new Object[]{ByteBuffer.wrap(request.getTotalData()), target, key});
        } catch (Exception e) {
        }
    }
    
    public void findNode(SelectionKey key, InetSocketAddress target, String targetId, Callbacker cb) {
        try {
            ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) addTransaction(key, cb));
            bb.flip();
            
            BencodeMap request = new BencodeMap();
            request.put(new BencodeString("t"), new BencodeString(new String(bb.array(), "iso-8859-1")));
            request.put(new BencodeString("y"), new BencodeString("q"));
            request.put(new BencodeString("q"), new BencodeString("find_node"));
            
            BencodeMap params = new BencodeMap();
            params.put(new BencodeString("id"), new BencodeString((String) ((Object[]) key.attachment())[3]));
            params.put(new BencodeString("target"), new BencodeString(targetId));
            request.put(new BencodeString("a"), params);

            requestDataQueue.put(new Object[]{ByteBuffer.wrap(request.getTotalData()), target, key});
        } catch (Exception e) {
        }
    }
    
    public void responsePing(SelectionKey key, InetSocketAddress target, String t) {
        try {
            BencodeMap response = new BencodeMap();
            response.put(new BencodeString("t"), new BencodeString(t));
            response.put(new BencodeString("y"), new BencodeString("r"));
            
            BencodeMap params = new BencodeMap();
            params.put(new BencodeString("id"), new BencodeString((String) ((Object[]) key.attachment())[3]));
            response.put(new BencodeString("r"), params);

            requestDataQueue.put(new Object[]{ByteBuffer.wrap(response.getTotalData()), target, key});
        } catch (Exception e) {
        }
    }
    
    public void responseFindNode(SelectionKey key, InetSocketAddress target, String t, String nodes) {
        try {
            BencodeMap response = new BencodeMap();
            response.put(new BencodeString("t"), new BencodeString(t));
            response.put(new BencodeString("y"), new BencodeString("r"));
            
            BencodeMap params = new BencodeMap();
            params.put(new BencodeString("id"), new BencodeString((String) ((Object[]) key.attachment())[3]));
            params.put(new BencodeString("nodes"), new BencodeString(nodes));
            response.put(new BencodeString("r"), params);

            requestDataQueue.put(new Object[]{ByteBuffer.wrap(response.getTotalData()), target, key});
        } catch (Exception e) {
        }
    }
    
    public void responseGetPeers(SelectionKey key, InetSocketAddress target, String t, String nodes, String token) {
        try {
            BencodeMap response = new BencodeMap();
            response.put(new BencodeString("t"), new BencodeString(t));
            response.put(new BencodeString("y"), new BencodeString("r"));
            
            BencodeMap params = new BencodeMap();
            params.put(new BencodeString("id"), new BencodeString((String) ((Object[]) key.attachment())[3]));
            params.put(new BencodeString("token"), new BencodeString(token));
            params.put(new BencodeString("nodes"), new BencodeString(nodes));
            response.put(new BencodeString("r"), params);

            requestDataQueue.put(new Object[]{ByteBuffer.wrap(response.getTotalData()), target, key});
        } catch (Exception e) {
        }
    }
    
    public void responseAnnouncePeer(SelectionKey key, InetSocketAddress target, String t) {
        try {
            BencodeMap response = new BencodeMap();
            response.put(new BencodeString("t"), new BencodeString(t));
            response.put(new BencodeString("y"), new BencodeString("r"));
            
            BencodeMap params = new BencodeMap();
            params.put(new BencodeString("id"), new BencodeString((String) ((Object[]) key.attachment())[3]));
            response.put(new BencodeString("r"), params);

            requestDataQueue.put(new Object[]{ByteBuffer.wrap(response.getTotalData()), target, key});
        } catch (Exception e) {
        }
    }
}

abstract class Callbacker {
    
    private int transactionId;
    
    public abstract void execute(BencodeMap response);
    public void destory() {}
    
    public final void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    
    public final int getTransactionId() {
        return transactionId;
    }
    
}

