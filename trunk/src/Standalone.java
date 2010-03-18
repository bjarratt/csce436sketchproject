import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.*;

/** Stand alone application 
 * - open / save sketch files
 * 
 * @author manoj
 *
 */
public class Standalone
{
	public static void main (String argv [])
    { 
 		GUIThread guiThread = new GUIThread();
        java.awt.EventQueue.invokeLater(guiThread);        
    }
}

class GUIThread implements Runnable
{

    public void run() 
    {
        new DrawFrame();
    }
}


/**
 * Frame containing a menu bar 
 * @author manoj
 *
 */
class DrawFrame extends JFrame implements ActionListener
{
	MenuBar menuBar;
	Menu filemenu, submenu;
	MenuItem menuItem;
	JFileChooser fc;
	
	JPanel panel;
	
	DrawPanel p1;
	DrawFrame()
	{
		p1=new DrawPanel();
		p1.setPreferredSize(new Dimension(500,500));
		p1.setBackground(Color.WHITE);
		
		// Creating the FileChooser Open/ Save Dialog box
		fc = new JFileChooser();
		
		panel = new JPanel();
		
		// Creating Menu Bar and adding File menu items
		menuBar = new MenuBar();		
		
		//Building the file menu
		filemenu = new Menu("File");
		filemenu.setActionCommand("file");
		menuBar.add(filemenu);
		
		//Adding Open menu item 
		menuItem = new MenuItem("Open");
		menuItem.setActionCommand("open");		
		menuItem.addActionListener(this);
		filemenu.add(menuItem);
		
		//Adding Save Menu item 
		menuItem = new MenuItem("Save");
		menuItem.setActionCommand("save");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);
		
		//Adding Exit Menu Item
		menuItem = new MenuItem("Exit");
		menuItem.setActionCommand("exit");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);
		
		//getContentPane().setLayout(new GridLayout());
		
		
		this.setMenuBar(menuBar);
		getContentPane().add(p1);		
		setSize(600,600);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}		

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equalsIgnoreCase("Open"))
		{
			// Open file dialog + loading the file into Sketch object
			int retval = fc.showOpenDialog(this);
			if(retval == JFileChooser.APPROVE_OPTION)
			{
				File f = fc.getSelectedFile();
				if(f.exists())
					p1.s.loadSketch(f);
			}
		}
		else if(arg0.getActionCommand().equalsIgnoreCase("Save"))
		{
			// Save file dialog + Saving the sketch object into a file 
			int retval = fc.showOpenDialog(this);
			if(retval == JFileChooser.APPROVE_OPTION)
			{
				File f = fc.getSelectedFile();
				
				try
				{
					
					FileWriter fw = new FileWriter(f);				
					String xml = p1.s.toXML();
					fw.write(xml);					
					fw.close();
					
				}catch(IOException e)
				{}
				
			}
			
		}
		else if(arg0.getActionCommand().equalsIgnoreCase("Exit"))
		{
			System.exit(0);
		}
		else
		{
			System.out.println("Not a File Menu");
		}
	}
}
  	