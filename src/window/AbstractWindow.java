package window;

import javax.swing.JFrame;

public abstract class AbstractWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public final void run() {
		initWindow(); // 对窗口的初始化操作
		init(); // 初始化界面
		regitstComponent(); // 注册组件
		addListener(); // 添加监听器
	}

	protected abstract void addListener();

	protected abstract void regitstComponent();

	protected abstract void init();

	protected void initWindow() {
		setSize(1000, 600);
		setLocationRelativeTo(null); // 设置居中显示
		setVisible(true);// 默认可见
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 关闭的默认操作：关闭窗口
	}
}
