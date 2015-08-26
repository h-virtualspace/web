package virtualspace_web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.here.framework.core.config.FastDfsConfig;
import com.here.framework.core.config.FastDfsConfig.Address;
import com.here.framework.core.file.fdfs.FastDfsClient;
import com.here.framework.core.file.fdfs.FdfsClientConfig;
import com.here.framework.core.file.fdfs.FdfsSocketPool;

/**
 * fastdfs  �ļ����Է���
 * @author koujp
 *
 */
public class FdfsFileTest {
	private FastDfsClient client=null;
	
	public FdfsFileTest(FastDfsClient client){
		this.client=client;
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		FastDfsConfig config=new FastDfsConfig();
		config.setCharset("utf-8");
		config.setConnect_timeout(30);
		config.setNetwork_timeout(30);
		config.setHttp_anti_steal_token(false);
		List<Address> tracker_servers=new ArrayList<FastDfsConfig.Address>();
		
		tracker_servers.add(new Address("101.200.233.161",22122));
		
		config.setTracker_servers(tracker_servers);
		
		config.setHttp_secret_key("FastDFS1234567890");
		
		FdfsClientConfig clientConfig=new FdfsClientConfig(config);
		
		FastDfsClient client = new FastDfsClient(clientConfig);
		
		
		
		FdfsFileTest fdfsTest=new FdfsFileTest(client);
		
		
		List<String> items=new ArrayList<String>();
		//items.add("/disk/picture/temp/upload.jpg");
		
		
		for(int i=0;i<1;i++){
			items.add("/disk/picture/temp/upload"+i+".jpg");
		}
		
		fdfsTest.upload(items);
		
		
		//fdfsTest.delete("M00/00/04/ZcjpoVV4O0aAJlSgAAUMHEhRpCg184.jpg");
		
	}
	public void delete(String file_name) throws Exception{
		int result=client.delete(file_name);
		System.out.println("-----"+result);
	}
	public void upload(List<String> items) throws Exception{
		
		int i=0;
		for(final String item:items){
//			i++;
//			Thread t=new Thread(){
//				 public void run(){
//					 try {
//						
//						String[] results=client.upload(new File(item),"jpg");
//						System.out.println("result:"+results[1]);
//					} catch (Exception e) {
//					
//						e.printStackTrace();
//					}
//				 }
//			};
//			
//			System.out.println("-------thread:"+t.getId()+"----started!");
//			t.start();
			 
			String[] results=client.upload(new File(item),"jpg");
			System.out.println("result:"+results[1]);
			//Thread.currentThread().sleep(1000);
		}
	}

}
