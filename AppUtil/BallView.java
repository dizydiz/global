package com.instagolf.AppUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.instagolf.R;

@SuppressLint("ViewConstructor")
public class BallView extends View{
	   private int xMax;
	   private int yMax;
	   private float ballY = 0;
	   private float ballStopX1,ballStopX2,ballStopX3,ballStopX5;
	   private float ballStopY1,ballStopY2,ballStopY3,ballStopY5;
	   private float ballStop;
	   
	   private Bitmap bitmap ;
	   private Matrix matrix;
	   
	   private int counter = 0;
	   private int iCurStep = 0;// current step
	   private Path path;
	   private PathMeasure pathMeasure;
	   
	   private boolean isFirstTime = true;
	   
	   private float fSegmentLen;
	   private int animationSteps;
	   
	   private float ballTargetHeight=0;
	   private float ballCurrHeight = 1;
	   private float density;
	   private Activity activity;
	   private Context context;
	   
	   private RelativeLayout rlMsg;
	   // Constructor
	   public BallView(Context context,Activity activity,RelativeLayout rlMsg) {
	      super(context);
	      
	      this.context = context;
	      this.rlMsg = rlMsg;
	      matrix = new Matrix();
	      path = new Path();
	     
	      this.activity = activity;
	      
	      DisplayMetrics dimension = new DisplayMetrics();
	      activity.getWindowManager().getDefaultDisplay().getMetrics(dimension);
	      xMax = dimension.widthPixels+40;
	      density = dimension.density;
	      yMax = (int) (dimension.heightPixels- (40 + (35*density))) ;
		 
	      ballStopX1=10;
	      ballStopX2=xMax/5;
	      ballStopX3=(float) (xMax/3.5);
//	      ballStopX4=(float) (xMax/2.1);
	      ballStopX5=(xMax/2)-5;
	      
		  ballStopY1=30;
		  ballStopY2=(float) (yMax/2.5);
		  ballStopY3=(float) (yMax/1.7);
//		  ballStopY4=(float) (yMax/1.3);
		  ballStopY5=yMax;
		  
		  ballStop = yMax;
	   }

	   @SuppressLint("DrawAllocation")
	@Override
	   protected void onDraw(Canvas canvas) {
		// Delay
		   if(isFirstTime){
			   isFirstTime=false;
			   try {  
			         Thread.sleep(100);  
			      } catch (InterruptedException e) { }
			   bounce();
		   }
		     
		   if(ballY  == ballStop){
			   return;
		   }
		   
		   if (iCurStep <= animationSteps) {
			   pathMeasure.getMatrix(fSegmentLen * iCurStep, matrix,
		            PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG);
		        
		        iCurStep++;
		        int tmp = (int) (ballCurrHeight +  ((ballTargetHeight * (iCurStep+1))/(animationSteps+1)));
		        if(tmp > ballTargetHeight){
		        	tmp = (int) ballTargetHeight;
		        }
		        resizeBall(tmp);
		        canvas.drawBitmap(bitmap, matrix, null); 
		        invalidate();
		    } else {
		    	counter++;
		    	invalidate();
		        iCurStep = 0;
		        if(counter==1){
		        	setMsgAnim();
		        	new PlaySound().playBeep(getContext(),activity);
		        }
		        bounce();
		    };
		    
	   }
	   
	   private void bounce(){
		   
		   switch (counter) {
		
		   case 0:
			   ballTargetHeight = 40;
			   path.reset();
			   animationSteps = 250;
			   path.moveTo(ballStopX1, ballStopY1);
			   path.quadTo((float) (ballStopX2)*2,(ballStopY1+ballStopY2)/4, ballStopX2,ballStopY2);

			   path.quadTo((float) (ballStopX3*1.2),(ballStopY2+ballStopY3)/3,ballStopX3,ballStopY3);

//			   path.quadTo((float) ((ballStopX3+ballStopX4)/1.5),(ballStopY3+ballStopY4)/2,ballStopX4,ballStopY4);
//
//			   path.quadTo((float) ((ballStopX4+ballStopX5)/2),(ballStopY4+ballStopY5)/2,ballStopX5,ballStopY5);
			   path.quadTo((float) ((ballStopX3+ballStopX5)/2),(ballStopY3+ballStopY5)/2,ballStopX5,ballStopY5);
			   pathMeasure = new PathMeasure(path, false);
			   fSegmentLen = pathMeasure.getLength() / animationSteps;//20 animation steps
			break;
			
		   case 1:
			   ballY  = ballStop;
			break;
//			

		}
	 }
	   
	   void resizeBall(int tmp){
//		   Log.e("target",">"+tmp);
		   bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		   bitmap = Bitmap.createScaledBitmap(bitmap, (tmp*bitmap.getWidth())/bitmap.getHeight(),tmp, true);
	   }
	   
	   void setMsgAnim(){
			 Animation animm = AnimationUtils.loadAnimation(context, R.anim.scale_up);
			 rlMsg.setVisibility(View.VISIBLE);
			 rlMsg.startAnimation(animm);
			 animm.setAnimationListener(new AnimationListener() {
				 @Override
				 public void onAnimationStart(Animation arg0) {}
				 @Override
				 public void onAnimationRepeat(Animation arg0) {}
				 @Override
				 public void onAnimationEnd(Animation arg0) {
					 // TODO Auto-generated method stub
					 Animation animm = AnimationUtils.loadAnimation(context, R.anim.scale_down);
					 rlMsg.startAnimation(animm);
					 animm.setFillAfter(true);
				 }
			 });
		}
	   
}
