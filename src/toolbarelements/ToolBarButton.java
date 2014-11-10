package toolbarelements;

import com.luttikdevelopment.vegimmanuel.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/*
 * Deprecation & NewApi caught without having any effect on application.
 */
@SuppressLint(
{ "RtlHardcoded", "InflateParams", "ClickableViewAccessibility", "NewApi" })
@SuppressWarnings("deprecation")
public class ToolBarButton extends RelativeLayout
{
	protected View background;
	protected ImageView foreGround;
	public Animation rotation;
	protected OnTouchListener listener;
	protected Context context;
	protected Animation animation;
	
	public ToolBarButton(Context context, int DrawableID)
	{
		super(context);
		this.context = context;
		initBackground();
		StandardInit(context.getResources().getDrawable(DrawableID));
	}

	public ToolBarButton(Context context, Drawable foregroundDrawable)
	{
		super(context);
		this.context = context;
		initBackground();
		StandardInit(foregroundDrawable);
	}
	
	public ToolBarButton(Context context, Drawable foregroundDrawable, View backgroundView, RelativeLayout.LayoutParams backgroundViewLayoutParams)
	{
		super(context);
		this.context = context;
		this.background = backgroundView;
		this.addView(backgroundView, backgroundViewLayoutParams);
		StandardInit(foregroundDrawable);
	}

	public void StandardInit(Drawable foregroundDrawable)
	{
		initSelf();
		initForeGround(foregroundDrawable);
		initRotation();
		setEvents();
	}
	
	protected void initSelf()
	{
		this.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	public void initForeGround(Drawable foregroundDrawable)
	{
		foreGround = new ImageView(context);
		foreGround.setImageDrawable(foregroundDrawable);
		float scale = context.getResources().getDisplayMetrics().density;
		RelativeLayout.LayoutParams foregroundLayoutParamethers = new RelativeLayout.LayoutParams((int)(32*scale), (int) (32*scale));
		foregroundLayoutParamethers.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		foregroundLayoutParamethers.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		this.addView(foreGround, foregroundLayoutParamethers);
	}
	
	protected void initBackground()
	{
		background = new View(context);
		background.setAlpha(0);
		float scale = context.getResources().getDisplayMetrics().density;
		
		ShapeDrawable backgroundShape = new ShapeDrawable(new OvalShape());
		backgroundShape.getPaint().setColor(Color.argb(50, 255, 255, 255));
		
		/* Split into before and after version 16 because of the replacement of setBackgroundDrawable by setBackground*/
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
		{
			background.setBackground(backgroundShape);
		} else
		{
			background.setBackgroundDrawable(backgroundShape);
		}

		RelativeLayout.LayoutParams backgroundLayoutParamethers = new RelativeLayout.LayoutParams((int)(45*scale), (int)(45*scale));
		backgroundLayoutParamethers.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		backgroundLayoutParamethers.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		this.addView(background, backgroundLayoutParamethers);
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

	/*
	 * returns true if there was an animation to cancel, returns false
	 * otherwise.
	 */
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
		Toolbar.LayoutParams lp = new Toolbar.LayoutParams(gravity);
		lp.setMargins(0, 0, 10, 0);
		toolbar.addView(this, lp);
	}
}
