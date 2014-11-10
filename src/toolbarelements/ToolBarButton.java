package toolbarelements;

import com.luttikdevelopment.vegimmanuel.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

@SuppressLint({ "RtlHardcoded", "InflateParams", "ClickableViewAccessibility" })
public class ToolBarButton
{
	protected View container;
	protected View background;
	protected ImageView foreGround;
	public Animation rotation;
	protected OnTouchListener listener;
	protected Context context;
	protected Animation animation;
	
	public ToolBarButton(Context context, int DrawableID)
	{
		init(context, context.getResources().getDrawable(DrawableID));
	}
	
	public ToolBarButton(Context context, Drawable drawable)
	{
		init(context, drawable);
	}
	
	public void init(Context context, Drawable drawable)
	{
		this.context = context;
		container = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.refreshbutton, null);
		background = container.findViewById(R.id.refresh_highlight);
		background.setAlpha(0);
		foreGround = (ImageView) container.findViewById(R.id.refresh_image_view);
		foreGround.setImageDrawable(drawable);
		
		initRotation();
		
		setEvents();		
	}
	
	protected void initRotation()
	{
		rotation = new RotateAnimation(0f, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotation.setDuration(500);
		rotation.setInterpolator(new LinearInterpolator());
		rotation.setRepeatCount(Animation.INFINITE);
	}
	
	protected void setEvents()
	{
		background.setOnTouchListener(new OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				doClickAnimation();
				listener.onTouch(v, event);
				return false;
			}
		});
	}
	
	protected void doClickAnimation()
	{
		Animation in = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);
		in.setDuration(50);
		in.setRepeatCount(0);
		
		Animation out = AnimationUtils.loadAnimation(context, R.anim.abc_fade_out);
		out.setDuration(300);
		out.setStartOffset(50);
		out.setRepeatCount(0);
		
		AnimationSet as = new AnimationSet(false);
		as.addAnimation(in);
		as.addAnimation(out);
		as.setAnimationListener(new AnimationListener()
		{
			public void onAnimationStart(Animation animation)
			{
				background.setAlpha(1);
			}
			
			public void onAnimationRepeat(Animation animation)
			{
				
			}
			
			public void onAnimationEnd(Animation animation)
			{
				background.setAlpha(0);
			}
		});
		
		background.startAnimation(as);
	}
		
	public void startAnimation(Animation a)
	{
		this.animation = a;
		foreGround.startAnimation(a);
	}
	
	/* returns true if there was an animation to cancel, returns false otherwise. */
	public void cancelAnimation()
	{
		foreGround.clearAnimation();
	}
	
	public void setOnTouchListener(OnTouchListener l)
	{
		listener = l;
	}

	public void addToToolbar(Toolbar toolbar)
	{
		addToToolbar(toolbar, Gravity.RIGHT);
	}
	
	public void addToToolbar(Toolbar toolbar, int gravity)
	{
		LayoutParams lp = new Toolbar.LayoutParams(gravity);
		lp.setMargins(0, 0, 10, 0);
		toolbar.addView(container, lp);
	}
}
