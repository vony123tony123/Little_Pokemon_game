/*
 * 107403549 資管三 涂建名
 */
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Main {

	private JFrame frame;
	private JTextField nickname_field;
	private JLabel picture_label;
	private JPanel buttonPanel;
	private JPanel gamecontrolPanel;
	private JPanel filecontrol_panel;
	private JLabel lblFilePath;
	private JButton btnAddCandy;
	private JLabel candy_label;
	private JLabel marklabel;
	private JLabel needlabel;
	private JButton btnLowdown;
	private JButton btnOpenGame;
	private JButton btnSave;
	private JButton btnSaveAs;
	
	private Map<String, Integer> firegrowMap;
	private Map<String, Integer> watergrowMap;
	private Map<String, Integer> grassgrowMap;
	private Map<String, Integer> currentGrowMap;
	private String choosePat[]= {"小火龍","傑尼龜","妙蛙種子"};
	private JComboBox<String> choosePatBox=new JComboBox<String>(choosePat);
	private Object obj[]= {"選擇你的御三家:",choosePatBox};
	private PokeSerializable currentPoke;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JOptionPane.showConfirmDialog(frame, obj,"大木博士",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		firegrowMap=IOTextFile.readRecords("fire.txt");
		watergrowMap=IOTextFile.readRecords("water.txt");
		grassgrowMap=IOTextFile.readRecords("grass.txt");
		System.out.println(grassgrowMap);
		
		switch(choosePatBox.getSelectedIndex())
		{
		case 0:
			currentPoke=new PokeSerializable("","f_s.png",0);
			currentGrowMap=firegrowMap;
			break;
		case 1:
			currentPoke=new PokeSerializable("","w_s.png",0);
			currentGrowMap=watergrowMap;
			break;
		case 2:
			currentPoke=new PokeSerializable("","g_s.png",0);
			currentGrowMap=grassgrowMap;
			break;
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		picture_label = new JLabel("");
		frame.getContentPane().add(picture_label, BorderLayout.NORTH);
		
		buttonPanel = new JPanel();
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		nickname_field = new JTextField();
		nickname_field.setHorizontalAlignment(SwingConstants.CENTER);
		nickname_field.setText("\u8A2D\u5B9A\u66B1\u7A31");
		buttonPanel.add(nickname_field);
		nickname_field.setColumns(10);
		
		gamecontrolPanel = new JPanel();
		buttonPanel.add(gamecontrolPanel);
		
		btnAddCandy = new JButton("\u7D93\u9A57\u7CD6\u679C");
		gamecontrolPanel.add(btnAddCandy);
		btnAddCandy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentPoke.setCandy(currentPoke.getCandy()+1);
				candy_label.setText(Integer.toString(currentPoke.getCandy()));
				if(currentPoke.getCandy()>=currentGrowMap.get(currentPoke.getMonster())&& currentPoke.getMonster().length()<=7 ) {
					Iterator iterator =currentGrowMap.keySet().iterator();
					String next="";
					while(iterator.hasNext()) {
						if(iterator.next().equals(currentPoke.getMonster()))
						{
							next=(String)iterator.next();
							System.out.println(next);
							break;
						}
					}
					
					
					
					if(!iterator.hasNext())
					{
						JOptionPane.showMessageDialog(frame, "進化成"+findname(next)+"，你好厲害!");
						currentPoke=new PokeSerializable(nickname_field.getText(),next,currentPoke.getCandy());
					}else {
						JOptionPane.showMessageDialog(frame, "進化成"+findname(next));
						currentPoke=new PokeSerializable(nickname_field.getText(),next,0);
					}
					Update();
				}
			}
		});
		
		candy_label = new JLabel("0");
		gamecontrolPanel.add(candy_label);
		
		marklabel = new JLabel("/");
		gamecontrolPanel.add(marklabel);
		
		needlabel = new JLabel("0");
		gamecontrolPanel.add(needlabel);
		
		btnLowdown = new JButton("退化");
		btnLowdown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator iterator =currentGrowMap.keySet().iterator();
				currentPoke=new PokeSerializable(nickname_field.getText(),iterator.next().toString(),0);
				Update();
			}
		});
		gamecontrolPanel.add(btnLowdown);
		
		filecontrol_panel = new JPanel();
		buttonPanel.add(filecontrol_panel);
		
		btnOpenGame = new JButton("Open Game");
		btnOpenGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Path path=getFilePath(true);
				lblFilePath.setText(path.toString());
				currentPoke=IOPostSerializable.readRecords(path.toString());
				if (firegrowMap.containsKey(currentPoke.getMonster())) {
					currentGrowMap=firegrowMap;
				}else if (watergrowMap.containsKey(currentPoke.getMonster())) {
					currentGrowMap=watergrowMap;
				}else if(grassgrowMap.containsKey(currentPoke.getMonster())) {
					currentGrowMap=grassgrowMap;
				}
				Update();
			}
		});
		filecontrol_panel.add(btnOpenGame);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lblFilePath.getText()=="New File")
					JOptionPane.showMessageDialog(frame, "非舊檔，如果要存檔請按SaveAs","警告",JOptionPane.WARNING_MESSAGE);
				else {
					currentPoke.setNickname(nickname_field.getText());
					IOPostSerializable.writeRecords(currentPoke, lblFilePath.getText());
				}
			}
		});
		filecontrol_panel.add(btnSave);
		
		btnSaveAs = new JButton("SaveAs");
		btnSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Path path=getFilePath(false);
				String s=JOptionPane.showInputDialog("請輸入檔名，需含副檔名:");
				currentPoke.setNickname(nickname_field.getText());
				System.out.println(path.toString());
				IOPostSerializable.writeRecords(currentPoke, path.toString()+"//"+s);
				lblFilePath.setText(path.toString()+"//"+s);
			}
		});
		filecontrol_panel.add(btnSaveAs);
		
		lblFilePath = new JLabel("New File");
		buttonPanel.add(lblFilePath);
		
		
		Update();
	}
	
	public void Update() {
		System.out.println(currentPoke.getMonster());
		picture_label.setIcon(new ImageIcon(currentPoke.getMonster()));
		nickname_field.setText(currentPoke.getNickname());
		candy_label.setText(Integer.toString(currentPoke.getCandy()));
		needlabel.setText(Integer.toString(currentGrowMap.get(currentPoke.getMonster())));
	}
	
	private Path getFilePath(boolean is_Open)
	   {
		JFileChooser fileChooser = new JFileChooser("\u9078\u64c7\u5132\u5b58\u8cc7\u6599\u593e");
		int result;
	      // configure dialog allowing selection of a file or directory
		if(is_Open==false) {
	      
	      fileChooser.setFileSelectionMode(
	         JFileChooser.DIRECTORIES_ONLY);
	      result=fileChooser.showSaveDialog(frame);
		}else {
			
		      fileChooser.setFileSelectionMode(
		         JFileChooser.FILES_AND_DIRECTORIES);
		      result=fileChooser.showOpenDialog(frame);
		}
		
		if(result!=JOptionPane.CANCEL_OPTION)
	      return fileChooser.getSelectedFile().toPath();
		else {
			return null;
		}
	   } 
	
	public String findname(String s) {
		System.out.println(s);
		if(s.equals("f_s.png"))
			return "小火龍";
		else if(s.equals("f_m.png"))
			return "火恐龍";
		else if(s.equals("f_l.png"))
			return "噴火龍";
		else if(s.equals("f_mega.png"))
			return "MEGA 噴火龍";
		else if(s.equals("w_s.png"))
			return "傑尼龜";
		else if(s.equals("w_m.png"))
			return "卡咪龜";
		else if(s.equals("w_l.png"))
			return "水箭龜";
		else if(s.equals("w_mega.png"))
			return "MEGA 水箭龜";
		else if(s.equals("g_s.png"))
			return "妙蛙種子";
		else if(s.equals("g_m.png"))
			return "妙蛙草";
		else if(s.equals("g_l.png"))
			return "妙蛙花";
		else if(s.equals("g_mega.png"))
			return "MEGA 妙蛙花";
		
		return null;
		
	}

}
