package com.hzc.keyboarpopupwindow;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 文件名：KeyboarPopupWindow 
 * 描 述：键盘菜单 
 * 作 者：HZC 
 * 时 间：2015-04-06
 * 
 * @param <T>
 */
@SuppressLint({ "HandlerLeak", "ViewConstructor" })
public class KeyboarPopupWindow<T> extends PopupWindow implements
		OnClickListener {
	public static int NUMBER_LENGTH = 4;// 允许输入的数量
//	private Context context;
	private boolean flag = true;
	private Button number0, number1, number2, number3, number4, number5,
			number6, number7, number8, number9, numberclean, numberDot;
	private StringBuilder stringBuilder = new StringBuilder();
	private TextView titleLeft, titleright;// 设置最上边文本的字数
	private EditText inputValue;
	private Button btnCancel, btnHistory, btnConfirm;// 底部的三个按钮
	private View myMenuView;
	private ImageView popImage;
	private T t;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//			if (stringBuilder.toString().contains(".")) {
//				NUMBER_LENGTH = 10;
//			} else {
//				NUMBER_LENGTH = 4;
//			}
			if (stringBuilder.length() <= NUMBER_LENGTH) {
				inputValue.setText(stringBuilder + "");
			} else {
				return;
			}
		};
	};

	public KeyboarPopupWindow(final Context context, final T t,final String key,
			OnClickListener itemsOnClick) {
		super(context);
//		this.context = context;
		this.t = t;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myMenuView = inflater.inflate(R.layout.number_keyboard_view, null);
		popImage = (ImageView) myMenuView.findViewById(R.id.pop_image);
		// inputValue = (TextView) myMenuView.findViewById(R.id.input_value);
		inputValue = (EditText) myMenuView.findViewById(R.id.input_value);
		titleLeft = (TextView) myMenuView.findViewById(R.id.tv_title_left);
		titleright = (TextView) myMenuView.findViewById(R.id.tv_title_right);
		number0 = (Button) myMenuView.findViewById(R.id.number0);
		number1 = (Button) myMenuView.findViewById(R.id.number1);
		number2 = (Button) myMenuView.findViewById(R.id.number2);
		number3 = (Button) myMenuView.findViewById(R.id.number3);
		number4 = (Button) myMenuView.findViewById(R.id.number4);
		number5 = (Button) myMenuView.findViewById(R.id.number5);
		number6 = (Button) myMenuView.findViewById(R.id.number6);
		number7 = (Button) myMenuView.findViewById(R.id.number7);
		number8 = (Button) myMenuView.findViewById(R.id.number8);
		number9 = (Button) myMenuView.findViewById(R.id.number9);
		numberclean = (Button) myMenuView.findViewById(R.id.numberclean);
		numberDot = (Button) myMenuView.findViewById(R.id.number_);
		inputValue.setOnClickListener(this);
		number0.setOnClickListener(this);
		number1.setOnClickListener(this);
		number2.setOnClickListener(this);
		number3.setOnClickListener(this);
		number4.setOnClickListener(this);
		number5.setOnClickListener(this);
		number6.setOnClickListener(this);
		number7.setOnClickListener(this);
		number8.setOnClickListener(this);
		number9.setOnClickListener(this);
		numberclean.setOnClickListener(this);
		numberDot.setOnClickListener(this);

		btnCancel = (Button) myMenuView.findViewById(R.id.btn_cancel);
		btnHistory = (Button) myMenuView.findViewById(R.id.btn_history);
		btnConfirm = (Button) myMenuView.findViewById(R.id.btn_confirm);

		// 设置按钮监听
		btnCancel.setOnClickListener(itemsOnClick);
		btnHistory.setOnClickListener(itemsOnClick);
		btnConfirm.setOnClickListener(itemsOnClick);

		// 设置KeyboarPopupWindow的View
		this.setContentView(myMenuView);
		// 设置KeyboarPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置KeyboarPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置KeyboarPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置KeyboarPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为透明
		ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
		// 设置KeyboarPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(colorDrawable);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.alpha_in);
		popImage.startAnimation(animation);
		// myMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		myMenuView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int height = myMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						Animation animation = AnimationUtils.loadAnimation(
								context, R.anim.alpha_out);
						popImage.startAnimation(animation);
						animation.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {
							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								new Handler().post(new Runnable() {
									@Override
									public void run() {
										dismiss();
									}
								});
							}
						});
					}
				}
				return true;
			}
		});

		// 让EditText不可获得焦点
		inputValue.setFocusable(false);
		// 监听EditText的文本改变事件
		inputValue.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if ("".equals(s.toString())) {
					return;
				}
			}
		});
	}

	// 获取key值
	public T getT() {
		return t;
	}

	// 设置标题
	public void setTitleLeft(String title) {
		titleLeft.setText(title);
	}

	// 获取标题
	public String getTitleLeft() {
		return titleLeft.getText().toString().trim();
	}

	// 设置单位
	public void setTitleRight(String title) {
		titleright.setText(" " + title);
	}

	// 获取单位名称
	public String getTitleRight() {
		return titleright.getText().toString().trim();
	}

	// 设置提示
	public void setInputValue(String title) {
		inputValue.setHint(title);
	}

	// 获取输入的数值
	public String getInputValue() {
		return inputValue.getText().toString().trim();
	}

	
	// 设置数值
	public void setValue(String value) {
		inputValue.setText(value);
		if (stringBuilder.length() != 0) {
			stringBuilder = new StringBuilder(value);
			updateMethod();
		}
	}
	
	@Override
	public void onClick(View v) {
		if (stringBuilder.length() >= NUMBER_LENGTH) {
			flag = false;
		} else {
			flag = true;
		}
		switch (v.getId()) {
		case R.id.number0:
			if (flag) {
				String zero = number0.getText().toString();
				stringBuilder.append(zero);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number1:
			if (flag) {
				String one = number1.getText().toString();
				stringBuilder.append(one);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number2:
			if (flag) {
				String two = number2.getText().toString();
				stringBuilder.append(two);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number3:
			if (flag) {
				String three = number3.getText().toString();
				stringBuilder.append(three);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number4:
			if (flag) {
				String four = number4.getText().toString();
				stringBuilder.append(four);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number5:
			if (flag) {
				String five = number5.getText().toString();
				stringBuilder.append(five);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number6:
			if (flag) {
				String six = number6.getText().toString();
				stringBuilder.append(six);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number7:
			if (flag) {
				String seven = number7.getText().toString();
				stringBuilder.append(seven);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number8:
			if (flag) {
				String eight = number8.getText().toString();
				stringBuilder.append(eight);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number9:
			if (flag) {
				String nine = number9.getText().toString();
				stringBuilder.append(nine);
				updateMethod();
			} else {
				return;
			}
			break;
		case R.id.number_:
			if (flag) {
				int indexOf = stringBuilder.indexOf(".");
				if (indexOf != -1 || stringBuilder.length() == 0) {
					return;
				} else {
					String dot = numberDot.getText().toString();
					stringBuilder.append(dot);
					updateMethod();
				}
			} else {
				return;
			}
			break;
		case R.id.numberclean:
			if (stringBuilder.length() != 0) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				updateMethod();
			} else {
				return;
			}
			break;
		default:
			break;
		}
	}

	/*
	 * 用于定时更新TextView
	 */
	private void updateMethod() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				handler.sendMessage(message);
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 10);
	}
}
