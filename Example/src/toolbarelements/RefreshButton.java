package toolbarelements;

import com.example.example.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.app.Activity;

/*
 * Deprecation & NewApi caught without having any effect on application.
 */
@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class RefreshButton extends ToolBarButton
{
	protected ImageView doneIcon;
	private Animation scaleanimation;
	private boolean animated = false;

	public RefreshButton(Context context, int DrawableID)
	{
		super(context, DrawableID);
		addDoneIcon(context);
		initScaleAnimation();
	}

	public void initScaleAnimation()
	{
		Animation scaleup = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);
		scaleup.setDuration(200);
		rotation.setInterpolator(new LinearInterpolator());

		scaleanimation = scaleup;
		scaleup.setAnimationListener(new AnimationListener()
		{
			public void onAnimationStart(Animation animation)
			{
				doneIcon.setVisibility(ImageView.VISIBLE);
			}

			public void onAnimationRepeat(Animation animation)
			{

			}

			public void onAnimationEnd(Animation animation)
			{
				foreGround.clearAnimation();
				Animation scaledown = AnimationUtils.loadAnimation(context, R.anim.abc_fade_out);
				scaledown.setDuration(300);
				scaledown.setStartOffset(2000);
				scaledown.setAnimationListener(new AnimationListener()
				{
					public void onAnimationStart(Animation animation)
					{

					}

					public void onAnimationRepeat(Animation animation)
					{

					}

					public void onAnimationEnd(Animation animation)
					{
						doneIcon.setVisibility(ImageView.INVISIBLE);
						getMe().setTouchable(true);
					}
				});
				doneIcon.clearAnimation();
				doneIcon.setAnimation(scaledown);
			}
		});
	}

	private RefreshButton getMe()
	{
		return this;
	}

	protected void addDoneIcon(Context context)
	{
		doneIcon = new ImageView(context);
		doneIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_done));
		doneIcon.setVisibility(ImageView.INVISIBLE);

		ShapeDrawable backgroundShape = new ShapeDrawable(new OvalShape());
		backgroundShape.getPaint().setColor(Color.WHITE);

		/*
		 * Split into before and after version 16 because of the replacement of
		 * setBackgroundDrawable by setBackground
		 */
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
		{
			doneIcon.setBackground(backgroundShape);
		} else
		{
			doneIcon.setBackgroundDrawable(backgroundShape);
		}

		float scale = context.getResources().getDisplayMetrics().density;
		RelativeLayout.LayoutParams paramethers = new RelativeLayout.LayoutParams((int) (32 * scale), (int) (32 * scale));
		paramethers.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		paramethers.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

		this.addView(doneIcon, paramethers);
	}

	/*
	 * Make sure you won't refresh to much.
	 */
	public void startAnimation(Animation a)
	{
		this.animation = a;
		animated = true;
		foreGround.startAnimation(a);
		setTouchable(false);
	}

	public void cancelAnimation()
	{
		((Activity)context).runOnUiThread(new Runnable()
		{
			public void run()
			{
				if (animated)
				{
					doneIcon.clearAnimation();
					doneIcon.startAnimation(scaleanimation);
				}
				getMe().setClickable(true);
			}
		});
	}
}
