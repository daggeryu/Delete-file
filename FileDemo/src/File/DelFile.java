package File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField; 
public class DelFile {
	//定义一个文件目录的层级
	static int level;
	//定义要删除的文件名
	private static String del1 =null;
    public static void main(String[] args) {    
        // 创建 JFrame 实例
        JFrame frame = new JFrame("文件删除工具");
        // Setting the width and height of frame
        frame.setSize(413, 220);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();    
        // 添加面板
        frame.add(panel);
        /* 
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
    	
        panel.setLayout(null);
        // 创建 JLabel
        JLabel selectFile = new JLabel("选择路径:");

        selectFile.setBounds(12,20,80,25);
        panel.add(selectFile);

        JTextField selectFileText = new JTextField(20);
        selectFileText.setBounds(91,20,225,25);
        panel.add(selectFileText);
        
        JButton selectButton = new JButton("选择");
        selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取选择文件夹
				try {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
//					int dialog = jfc.showDialog(new JLabel(), "保存");
					int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
					File file = jfc.getSelectedFile();// file为选择到的目录
					selectFileText.setText(file.toString());
				} catch (Exception e2) {
					
				}
				
			}
		});
        selectButton.setBounds(330,20,80,25);
        panel.add(selectButton);
        //需要执行的操作
        JLabel operation = new JLabel("执行操作:");
        operation.setBounds(12, 50, 80, 25);
        panel.add(operation);
        //下拉框
        JComboBox cmb=new JComboBox();    //创建JComboBox
        cmb.addItem("--请选择--");    //向下拉列表中添加一项
        cmb.addItem("删除以为**开头文件");
        cmb.addItem("删除以为**结尾文件");
        cmb.addItem("删除文件名为**的文件");
        cmb.setBounds(92, 50, 225, 25);
        panel.add(cmb);
        
        // 设置条件值
        JLabel condition = new JLabel("输入条件:");

        condition.setBounds(12,80,80,25);
        panel.add(condition);

        JTextField conditionText = new JTextField(20);
        conditionText.setBounds(91,80,225,25);
        panel.add(conditionText);
        // 创建登录按钮
        JButton loginButton = new JButton("开始执行");
        loginButton.setBounds(165, 110, 80, 25);
        panel.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(cmb.getSelectedItem().toString()+selectFileText.getText()+"  "+conditionText.getText());
			     //创建一个目录对象 文件路径随意更换
		        File file = new File(selectFileText.getText());
//		        System.out.println("运行");
//		        parseFile(file,conditionText.getText()); 
		        if (conditionText.getText().equals("") || selectFileText.getText().equals("") ) {
		        	JOptionPane.showMessageDialog(panel, "清输入条件或者选择路径", "消息提示",JOptionPane.WARNING_MESSAGE);  
		        	return;
		        }else {

		        	// 确认修改物品信息提示
		        	int response = JOptionPane.showConfirmDialog(panel, "确认要删除吗？", "删除文件", JOptionPane.YES_NO_OPTION);
		        	if (response == JOptionPane.YES_OPTION) {
		        		if (cmb.getSelectedItem().toString().equals("删除以为**开头文件")) {
				        	startsnameFile(file,conditionText.getText());  
						}else if (cmb.getSelectedItem().toString().equals("删除以为**结尾文件")) {
							endsnameFile(file, conditionText.getText());
						}
						else if (cmb.getSelectedItem().toString().equals("删除文件名为**的文件")) {
							fileNameWith(file, conditionText.getText());
						}
				        JOptionPane.showMessageDialog(panel, "已经完成", "提示信息",JOptionPane.WARNING_MESSAGE);  
				        System.out.println("结束");	
		        	}
				}
			}
		});
    }

    //删除以**开头的文件
    public static void startsnameFile(File file,String filename) {
        //给层级来加一
        level++;
        //获得文件对象的子文件对象列表
        File[] filess = file.listFiles();
        String[] files = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(filename);
            }
        });
        //过滤以什么开头的文件
        for (File f : filess){
        	for (String string : files){
            	if (f.getName().equals(string)) {
	        		System.out.println(f.getAbsoluteFile());
	        		f.delete();					
            	} 
        	}
            if(f.isDirectory()){
                //递归的方式来遍历
            	startsnameFile(f,filename);
            }
		}
        //本层次遍历完毕把层级减回来
        level--;
    }
    //删除以**开头的文件
    public static void endsnameFile(File file,String filename) {
        //给层级来加一
        level++;
        //获得文件对象的子文件对象列表
        File[] filess = file.listFiles();
        String[] files = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(filename);
            }
        });
        //过滤以什么开头的文件
        for (File f : filess){
        	for (String string : files){
//        		System.out.println(string);
            	if (f.getName().equals(string)) {
	        		System.out.println(f.getAbsoluteFile());
	        		f.delete();					
            	} 
        	}
            if(f.isDirectory()){
                //递归的方式来遍历
            	endsnameFile(f,filename);
            }
		}
        //本层次遍历完毕把层级减回来
        level--;
    }
    //删除文件名为**的文件
    public static void fileNameWith(File file,String name) {
        if(file == null || !file.exists()) {
            return;
        }
        //给层级来加一
        level++;
        //获得文件对象的子文件对象列表
        File[] filess = file.listFiles();      
        //遍历这些子文件
        for (File f : filess){
  
            for (int i = 0; i < level; i++)
            	//要删除文件的文件名
            	//找到文件名想同的就删除
            	if (f.getName().equals(name)) {
            		System.out.println(f.getAbsoluteFile());
            		f.delete();					
				} 
            //判断这些子文件是否是目录
            if(f.isDirectory()){
                //递归的方式来遍历
            	fileNameWith(f,name);
            }
        }
        //本层次遍历完毕把层级减回来
        level--;
    }
}