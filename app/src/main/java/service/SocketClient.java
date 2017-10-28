package service;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
/**
 * �ͻ���Socket������ ������ʽ
 * @author Kesion
 * 2017��9��28��
 */
public class SocketClient {
	
	/**
	 * ���û����ֽ�����С
	 */
	private static final int BUFFER_SIZE = 1024;
	
	/**
	 * Ĭ���ַ�����
	 */
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * ȷ���Ƿ������Ӵ���
	 */
	private static final int CONNECTION_TIMES = 20;

	/**
	 * ��������
	 */
	private static final int MAX_RECONNECTION_TIMES = 50;

	/**
	 * ����ʱ��
	 */
	private static final long RECONNECTION_TIME_LENGRH = 1000*60;
	
	/**
	 * �ͻ���SocketChannel ����������Ӷ���
	 */
	private SocketChannel  socketChannel = null;
	/**
	 * ���ֽ��������С
	 */
	private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	/**
	 * д�ֽ��������С
	 */
	private ByteBuffer sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	
	/**
	 * �����ַ�����
	 */
	private Charset cherSet  = Charset.forName(DEFAULT_CHARSET);
	
	/**
	 * ���ӵ�ַ
	 */
	InetSocketAddress isa = null;
	
	
	/**
	 * �����߳�
	 */
	Thread listenerSelectThread = null;
	
	private Selector selector  = null;
	
	private String hostName;
	
	private int port;
	/**
	 * �ַ���������Ϣ����
	 */
	private SocketStringMessageListener socketStringMessageListener = null;
	
	
	/**
	 * ����������Ϣ����
	 */
	private SocketObjectMessageListener socketObjectMessageListener = null;

	private boolean isSocketClose = false;

	public SocketClient(String hostName,int port){
		this.hostName = hostName;
		this.port = port;
	}
	
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO ��������
	 * void
	 */
	public boolean connection(){
		
		
		
		try {
			
			if(socketChannel!=null&&socketChannel.isConnected())
				return true;
			
			
			if(selector==null &&socketChannel == null || !socketChannel.isConnected()){
				isa=new InetSocketAddress(hostName,port);  
				selector = Selector.open();
				socketChannel = SocketChannel.open();
			
				//�����Է�������ʽ����  
				socketChannel.configureBlocking(false);  

			} 
			/**
			 * ��ע�ᵽselect �������� ����cpuʹ����Ʈ��
			 */
			//��Socketchannel����ע�ᵽָ��Selector  	
			socketChannel.register(selector, SelectionKey.OP_READ );
			
			boolean isConnection = socketChannel.connect(isa);
			
			int conntionTimes=0;
			if(!isConnection){

				//ȷ���Ƿ�����
				while(!socketChannel.finishConnect()){
					
					if(conntionTimes < CONNECTION_TIMES){
						conntionTimes++;
					}else{
						closeSocket();
						return false;
					}
					
				}
				
			}

			isSocketClose = false;
			startSocketListener();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���Ӵ���");
			Log.d("TAG_KE_MESSAGE","fail"+e.getMessage());
			/**
			 * �ر�����
			 */
			closeSocket();
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO �������ݼ���
	 * void
	 */
	private void startSocketListener() {
		if(listenerSelectThread == null || listenerSelectThread.isInterrupted()){
			
			listenerSelectThread = null;
			System.out.println("thread");
		
			
			listenerSelectThread = new Thread(){
				public void run() {
					talkListener();
					
				};
				
			};
			
			listenerSelectThread.start();
		}
	
	}
	
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO ��������
	 * void
	 */
	private void reStartConnection(){
		
		int times=0;
		
		closeSocket();
		
		
		while(times < MAX_RECONNECTION_TIMES){
			
			times++;
			Log.d("TAG_KE_MESSAGE","ReConnection");
			if(connection()){

				break;
			}

			try {
				Thread.sleep(RECONNECTION_TIME_LENGRH);
			} catch (InterruptedException e) {
				e.printStackTrace();
				//System.out.println("");
			}
		}
		
		if(times == MAX_RECONNECTION_TIMES){
			System.out.println("����ʧ��");
		}
		
	}

	/**
	 * @Author Kesion
	 * 2017��9��28��
	 * TODO �����ַ�����
	 * @param charSet ��������
	 * void
	 */
	public void setCharSet(String charSet){
		this.cherSet = Charset.forName(charSet);
	}
	
	
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO �ر�socket
	 * void
	 */
	public void closeSocket(){
		
		if(selector!=null&&selector.isOpen()){
			
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				selector = null;
			}
		}
		
		if(socketChannel!=null){
			try {
				socketChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO select ���ݼ���
	 * void
	 */
	private void talkListener(){
		
		
		if(listenerSelectThread != null && !listenerSelectThread.isInterrupted()){
	
			try {
				
				 while(!listenerSelectThread.isInterrupted()){

					 Log.d("TAG_KE_MESSAGE","talkListener");

					 try {
						 int sel = selector.select();

						 if (sel  > 0 ) {
							 Log.d("TAG_KE_MESSAGE","sel" +sel);


							 //	System.out.println("come");
							 Log.d("TAG_KE_MESSAGE", "selector");


						/*	 Set<SelectionKey> readySet = selector.selectedKeys();
							 Iterator<SelectionKey> iterator = readySet.iterator();*/

							 Log.d("TAG_KE_MESSAGE","size:"+selector.selectedKeys().size()+"");

							for (SelectionKey key : selector.selectedKeys()) {
								selector.selectedKeys().remove(key);
								Log.d("TAG_KE_MESSAGE","key:"+key.interestOps());
								if( key.isReadable() ){

									receive(key);

									key.interestOps(SelectionKey.OP_READ);
								}
								key=null;
							}
							 selector.selectedKeys().clear();


							 if(isSocketClose)
								 break;
							/* while (iterator.hasNext()) {

								 SelectionKey key = null;
								 key = (SelectionKey) iterator.next();

								 iterator.remove();
								 if (key.isReadable()) {

									 receive(key);


								 }

								 if (key.isWritable()) {


								 }

							 }//end while iterator*/

						 }//#end while selector
					 }catch (IOException e){
						 Log.d("TAG_KE_MESSAGE","select fail");
					/*	listenerSelectThread.interrupt();
						listenerSelectThread = null;*/
						 e.printStackTrace();
					 }
					
				 }//#end while inpurrent
				
				 /**
				 * �̱߳��жϻ򴴽��߳�ʧ�������³�������
				 *//*
				startSocketListener();
				return ;*/
			} catch (Exception e) {
				Log.d("TAG_KE_MESSAGE","kill connection");
			/*	listenerSelectThread.interrupt();
				listenerSelectThread = null;*/
				e.printStackTrace();
				//reStartConnection();

			//	System.out.println(e.getMessage());
				
			}

			if(isSocketClose){
				listenerSelectThread.interrupt();
				listenerSelectThread = null;
				reStartConnection();
			}

		}else{
			
			/**
			 * �̱߳��жϻ򴴽��߳�ʧ�������³�������
			 */
			//startSocketListener();
		}
	}


	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO ���շ�������Ϣ
	 * @param key
	 * void
	 * @throws IOException 
	 */
	private void receive(SelectionKey key) throws IOException {

		SocketChannel socketChannel = (SocketChannel)key.channel();  
		
		if(socketStringMessageListener!=null){
			
			String message="";
			
			while(socketChannel.read(readBuffer)>0){
				readBuffer.flip();
				message+=decode(readBuffer);
				readBuffer.clear();
			}

			isSocketClose(message.length());

			socketStringMessageListener.receiveStringMessage(message);
			
		}
		
		
		if(socketObjectMessageListener!=null){
			
			Socket socket = socketChannel.socket();
			
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			
			try {
				socketObjectMessageListener.receiveObjectMessage(inputStream.readObject());
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}

		//key.cancel();
	
	}
	
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO �ַ�����
	 * @param buffer
	 * @return
	 * String
	 */
	public String decode(ByteBuffer buffer)
	{ 
		CharBuffer charBuffer = cherSet.decode(buffer);
		return charBuffer.toString();
	}


	public void isSocketClose(int length) {
		if(length==0)
			isSocketClose =true;
		else
			isSocketClose = false;
	}




	/**
	 * String�������ݼ����ӿ�
	 * @author Kesion
	 * 2017��9��29��
	 */
	public interface SocketStringMessageListener{
		/**
		 * 
		 * @Author Kesion
		 * 2017��9��29��
		 * TODO �����ַ�����Ϣ
		 * @param message
		 * void
		 */
		public void receiveStringMessage(String message);
	}
	
	/**
	 * 
	 * @Author Kesion
	 * 2017��9��29��
	 * TODO �����ַ����������ݼ���
	 * @param socketStringMessageListener
	 * void
	 */
	public void setOnSocketStringMessageListener(SocketStringMessageListener socketStringMessageListener){
		this.socketStringMessageListener = socketStringMessageListener;
	}
	
	/**
	 * Object�������ݼ����ӿ�
	 * @author Kesion
	 * 2017��9��29��
	 */
	public interface SocketObjectMessageListener{
		/**
		 * 
		 * @Author Kesion
		 * 2017��9��29��
		 * TODO ����Object��Ϣ
		 * void
		 */
		public void receiveObjectMessage(Object objMessage);
	}
	
	public void setOnSocketObjectMessageListener(SocketObjectMessageListener socketObjectMessageListener){
		this.socketObjectMessageListener = socketObjectMessageListener;
	}
	
	
}
