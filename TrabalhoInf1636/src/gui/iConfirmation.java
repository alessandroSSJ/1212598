package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import observersxadrez.TabObserver;
import observersxadrez.iTabObserver;

public class iConfirmation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int HEIGHT =  100  ;
	private static final int WIDTH =   300;

	public static final int OK = 0;
	public static final int NOTOK = 1;
	public static final int IND = 2;
	
	private static int confirmation = IND;
	
	private String messageLabel;
	
	private static iConfirmation window;
	
	private iConfirmation(String message)
	{
		super("Confirmação");
		
		confirmation = IND;
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		Dimension screenSize=tk.getScreenSize();
		
		int x = screenSize.width/2 - WIDTH/2   ;
		int y = screenSize.height/2 - HEIGHT/2 ;
		
		setLayout(null);
		
		setBounds(x,y,WIDTH,HEIGHT); 
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		setResizable(false);
		
		messageLabel = message;
	}
	
	public static iConfirmation getWindow( String message )
	{
		if (window == null)
			window = new iConfirmation(message);
		
		return window;
	}
	
	public void getResponse()
	{
		Insets in = this.getInsets();
		
		JButton sim = new JButton("Sim");
	
		Dimension size = sim.getPreferredSize();
		
		sim.setBounds(55+in.left, 40+in.top,size.width,size.height);
		
		sim.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				iConfirmation.setResponse(iConfirmation.OK);
				TabObserver.Notifica();
				iTabObserver.Notifica();
		      } 
		});
		
		JButton nao = new JButton("Nao"); 
	
		size = nao.getPreferredSize();
		
		nao.setBounds(170+in.left, 40+in.top,size.width,size.height);
		
		nao.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
		      {
				iConfirmation.setResponse(iConfirmation.NOTOK);
				window.setVisible(false);
				window.dispose();
		      } 
		});
		
		JLabel men = new JLabel(messageLabel);
		men.setFont(new Font("Papyrus", Font.ITALIC, 17));
	
		size = men.getPreferredSize();
		
		men.setBounds(45+in.left, 5+in.top,size.width,size.height);
		
		this.add("West",sim);
		this.add("East",nao);
		this.add("North",men);
		this.setVisible(true);
		
	}
	
	public static void setResponse(int a)
	{
		confirmation = a ;
	}
	
	public static int readResponse()
	{
		return confirmation;
	}
	
	public static void close()
	{
		if ( window != null )
		{
			window.setVisible(false);
			window.dispose();
			window = null;
		}
	}
}
