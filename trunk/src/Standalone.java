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

                menuItem = new MenuItem("[OFF] Size Speed Option");
		menuItem.setActionCommand("size speed option");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                 menuItem = new MenuItem("[OFF] Rainbow");
		menuItem.setActionCommand("rainbow");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                 menuItem = new MenuItem("[OFF] Disco Pen");
		menuItem.setActionCommand("disco pen");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                 menuItem = new MenuItem("[OFF] Color Band");
		menuItem.setActionCommand("color band");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                 menuItem = new MenuItem("[OFF] Double Stroke");
		menuItem.setActionCommand("double stroke");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                 menuItem = new MenuItem("[OFF] Mirror Stroke");
		menuItem.setActionCommand("mirror stroke");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                menuItem = new MenuItem("[OFF] Jitter");
		menuItem.setActionCommand("jitter");
		menuItem.addActionListener(this);
		filemenu.add(menuItem);

                menuItem = new MenuItem("Clear Screen");
		menuItem.setActionCommand("clear screen");
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
                else if(arg0.getActionCommand().equalsIgnoreCase("Size Speed Option"))
		{
			// Save file dialog + Saving the sketch object into a file
			
                            if(p1.speedSize)
                            {
                                p1.speedSize = false;
                            }
                            else
                            {
                                p1.speedSize = true;
                            }
                       
                }

                else if(arg0.getActionCommand().equalsIgnoreCase("Rainbow"))
		{
			// Save file dialog + Saving the sketch object into a file

                            if(p1.rainbow)
                            {
                                p1.rainbow = false;
                            }
                            else
                            {
                                p1.rainbow = true;
                                p1.discoPen = false;
                                p1.colorBand = false;
                            }
                                

                }

                else if(arg0.getActionCommand().equalsIgnoreCase("Disco Pen"))
		{
			// Save file dialog + Saving the sketch object into a file

                            if(p1.discoPen)
                            {
                                p1.discoPen = false;
                            }
                            else
                            {
                                p1.discoPen = true;
                                p1.rainbow = false;
                                p1.colorBand = false;
                            }

                }

                else if(arg0.getActionCommand().equalsIgnoreCase("Color Band"))
		{
			// Save file dialog + Saving the sketch object into a file

                            if(p1.colorBand)
                                p1.colorBand = false;
                            else
                            {
                                p1.colorBand = true;
                                p1.rainbow = false;
                                p1.rainbow = false;
                            }

                }

                else if(arg0.getActionCommand().equalsIgnoreCase("Double Stroke"))
		{
			// Save file dialog + Saving the sketch object into a file

                            if(p1.Stan.doubleStroke)
                            {
                                p1.Stan.doubleStroke = false;
                            }
                            else
                            {
                                p1.Stan.doubleStroke = true;
                                p1.Stan.mirrorStroke = false;
                            }

                            if(p1.doubleStroke)
                            {
                                p1.doubleStroke = false;
                            }
                            else
                            {
                                p1.doubleStroke = true;
                                p1.mirrorStroke = false;
                            }

                }

                else if(arg0.getActionCommand().equalsIgnoreCase("Mirror Stroke"))
		{
			// Save file dialog + Saving the sketch object into a file

                            if(p1.Stan.mirrorStroke)
                            {
                                p1.Stan.mirrorStroke = false;
                            }
                            else
                            {
                                p1.Stan.mirrorStroke = true;
                                p1.Stan.doubleStroke = false;
                            }

                            if(p1.mirrorStroke)
                            {
                                p1.mirrorStroke = false;
                            }
                            else
                            {
                                p1.mirrorStroke = true;
                                p1.doubleStroke = false;
                            }

                }

                else if(arg0.getActionCommand().equalsIgnoreCase("jitter"))
		{
			// Save file dialog + Saving the sketch object into a file

                            if(p1.Stan.jitter>-0)
                                p1.Stan.jitter = -0;
                            else
                                p1.Stan.jitter = 8;

                }

                else if(arg0.getActionCommand().equalsIgnoreCase("Clear Screen"))
		{
			// Save file dialog + Saving the sketch object into a file

                            p1.s.strokeList.clear();
                            repaint();

                }


                
		else if(arg0.getActionCommand().equalsIgnoreCase("Exit"))
		{
			System.exit(0);
		}
		else
		{
			System.out.println("Not a File Menu");
		}
		
		int numMenuItemValues = 7;
		int menuItemOffset = 2;
		boolean menuItemValues[] = new boolean[numMenuItemValues];
		menuItemValues[0] = p1.speedSize;
		menuItemValues[1] = p1.rainbow;
		menuItemValues[2] = p1.discoPen;
		menuItemValues[3] = p1.colorBand;
		menuItemValues[4] = p1.doubleStroke;
		menuItemValues[5] = p1.mirrorStroke;
		menuItemValues[6] = p1.Stan.jitter > 0;
		
		// Strip away [ON] and [OFF]
		for (int i = 0; i < numMenuItemValues; i++)
		{
			String menuStr = this.filemenu.getItem(i + menuItemOffset).getLabel();
			menuStr = menuStr.split(" ", 2)[1];
			if (menuItemValues[i])
			{
				menuStr = "[ON] " + menuStr;
			}
			else
			{
				menuStr = "[OFF] " + menuStr;
			}
			this.filemenu.getItem(i + menuItemOffset).setLabel(menuStr);
		}
		
		
	}
}
  	