
public class JavaHook {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LoadLibrary：先用自己的程序load目标库  
		HMODULE hModule = LoadLibrary(_T("user32.dll"));   
		//GetProcAddress：在目标中找到自己想hook的函数地址  
		pOldAPI = (pDefaultAPI)GetProcAddress(hModule, "MessageBoxW");  
	}

}
