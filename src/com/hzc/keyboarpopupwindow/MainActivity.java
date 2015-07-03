package com.hzc.keyboarpopupwindow;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity {
	private KeyboarPopupWindow<?> keyboarMenuWindow;
	
	private TextView tvShow;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvShow = (TextView) findViewById(R.id.tv_show);
		button = (Button) findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 键盘
				keyboarMenuWindow = new KeyboarPopupWindow(MainActivity.this,
						"", "",itemsOnClick);
				// 显示窗口
				keyboarMenuWindow.showAtLocation(findViewById(R.id.main),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
				keyboarMenuWindow.setTitleLeft("体温");
				keyboarMenuWindow.setInputValue("请输入体温数");
				keyboarMenuWindow.setTitleRight("℃");
			}
		});
	}
	
	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			keyboarMenuWindow.dismiss();
			keyboarMenuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_cancel:
				Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_history:
				Toast.makeText(MainActivity.this, "点击了历史记录", Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_confirm:
				Toast.makeText(MainActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
				tvShow.setText(keyboarMenuWindow.getInputValue());
				break;
			default:
				break;
			}
		}
	};
}
