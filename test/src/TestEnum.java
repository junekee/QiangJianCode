

/**
 * ö���÷����
 * 
 * @author jiqinlin
 * 
 */
public class TestEnum implements enumtest{
    /**
     * ��ͨö��
     * 
     * @author jiqinlin
     *
     */
    public enum ColorEnum {
        red, green, yellow, blue;
    }
    
    /**
     * ö������ͨ����һ������������Ժͷ���������Ϊ����Ӿ�̬�ͷǾ�̬�����Ի򷽷�
     * 
     * @author jiqinlin
     *
     */
    public enum SeasonEnum {
        //ע��ö��д����ǰ�棬����������
        spring, summer, autumn, winter;

        private final static String position = "test";

        public static SeasonEnum getSeason() {
            if ("test".equals(position))
                return spring;
            else
                return winter;
        }
    }
    public enum kv{
    	add("add");
    	private String method;
    	private kv(String method){
    		this.method=method;
    	}
    	 //���Ƿ���  
        @Override  
        public String toString() {  
            return this.method;  
        }  
    }
    /**
     * �Ա�
     * 
     * ʵ�ִ��й�������ö��
     * 
     * @author jiqinlin
     *
     */
    public enum Gender{
        //ͨ�����Ÿ�ֵ,���ұ������һ���ι�������һ�����Ը�����������������
        //��ֵ���붼��ֵ�򶼲���ֵ������һ���ָ�ֵһ���ֲ���ֵ���������ֵ����д����������ֵ����Ҳ����
//        MAN("MAN"), WOMEN("WOMEN"),
        Print {
        	@Override
			public void printEnum() {
				System.out.println("Hello! enum");
			};
        };
        public abstract void printEnum();	
//        private final Object value;
//
//        //������Ĭ��Ҳֻ����private, �Ӷ���֤���캯��ֻ�����ڲ�ʹ��
//        Gender(Object value) {	
//            this.value = value;
//        }
//        
//        public Object getValue() {
//            return value;
//        }
    }
    
   /**
    * ����״̬
    * 
    * ʵ�ִ��г��󷽷���ö��
    * 
    * @author jiqinlin
    *
    */
    public enum OrderState {
        /** ��ȡ�� */
        CANCEL {public String getName(){return "��ȡ��";}},
        /** ����� */
        WAITCONFIRM {public String getName(){return "�����";}},
        /** �ȴ����� */
        WAITPAYMENT {public String getName(){return "�ȴ�����";}},
        /** ������� */
        ADMEASUREPRODUCT {public String getName(){return "�������";}},
        /** �ȴ����� */
        WAITDELIVER {public String getName(){return "�ȴ�����";}},
        /** �ѷ��� */
        DELIVERED {public String getName(){return "�ѷ���";}},
        /** ���ջ� */
        RECEIVED {public String getName(){return "���ջ�";}};
        
        public abstract String getName();
    }
    
    public static void main(String[] args) {
        //ö����һ�����ͣ����ڶ�������������Ʊ����ĸ�ֵ����ֵʱͨ����ö����.ֵ��ȡ��ö���е�ֵ
        ColorEnum colorEnum = ColorEnum.blue;
        switch (colorEnum) {
        case red:
            System.out.println("color is red");
            break;
        case green:
            System.out.println("color is green");
            break;
        case yellow:
            System.out.println("color is yellow");
            break;
        case blue:
            System.out.println("color is blue");
            break;
        }
        
        //����ö��
        System.out.println("����ColorEnumö���е�ֵ");
        for(ColorEnum color : ColorEnum.values()){
            System.out.println(color);
        }
        
        //��ȡö�ٵĸ���
        System.out.println("ColorEnumö���е�ֵ��"+ColorEnum.values().length+"��");
        
        //��ȡö�ٵ�����λ�ã�Ĭ�ϴ�0��ʼ
        System.out.println(ColorEnum.red.ordinal());//0
        System.out.println(ColorEnum.green.ordinal());//1
        System.out.println(ColorEnum.yellow.ordinal());//2
        System.out.println(ColorEnum.blue.ordinal());//3
        
        //ö��Ĭ��ʵ����java.lang.Comparable�ӿ�
        System.out.println(ColorEnum.red.compareTo(ColorEnum.green));//-1
        
        //--------------------------
        System.out.println("===========");
        System.err.println("����Ϊ" + SeasonEnum.spring);
        
        
        //--------------
        System.out.println("111===========");
        for(Gender gender : Gender.values()){
//            System.out.println(gender.value);
        }
       
        //--------------
        System.out.println("===========");
        for(OrderState order : OrderState.values()){
            System.out.println(order.getName());
        }
        OrderState.CANCEL.getName();
        Gender.valueOf("Print").printEnum();
        
        System.out.println(method.save.toString());
        System.out.println(method.add);
    }
    
    
    public void printEnum(){
    	System.out.println("Hello! enum");
    }
}
