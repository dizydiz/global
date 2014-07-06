package com.sportsbetting.AppAdapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sportsbetting.R;
import com.sportsbetting.SponsorDetail;
import com.sportsbetting.AppUtil.FontStyle;
import com.sportsbetting.GlobalData.GlobalField;
import com.sportsbetting.ImageLoader.ImageLoader;
import com.sportsbetting.Model.SponsorModel;

public class SponsorsListAdapter extends BaseAdapter{
	private ArrayList<SponsorModel> myArrayList;
	private Context context;
	private Activity activity;
	private int IMAGE_SIZE;
	private ImageLoader imageLoader;
	private FontStyle fontStyle;
	public SponsorsListAdapter(ArrayList<SponsorModel> myArrayList,Context context, Activity activity){
		this.myArrayList = myArrayList;
		this.context=context;
		this.activity = activity;
		DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
		IMAGE_SIZE = (int) (metrics.density*70);
		imageLoader = new ImageLoader(this.context, R.drawable.no_image);
		fontStyle = new FontStyle(this.context);
	}
	@Override
	public int getCount() {
		return myArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View myView = convertView;
		final ViewHolder holder;
		 
		if(myView == null){
			LayoutInflater inflater  = LayoutInflater.from(context);
			myView = inflater.inflate(R.layout.style_sponsor_list,null );
			holder = new ViewHolder();
			 
			holder.tvSponserName= (TextView)myView.findViewById(R.id.tvSponserName);
			holder.tvSponserDetail= (TextView)myView.findViewById(R.id.tvSponserDetail);
			holder.ivIcon= (ImageView)myView.findViewById(R.id.ivIcon);
			
			fontStyle.setHelveticaRegular(holder.tvSponserName);
			fontStyle.setHelveticaRegular(holder.tvSponserDetail);
			
			holder.rlNext= (RelativeLayout)myView.findViewById(R.id.rlNext);
			myView.setTag(holder);
		}else{
			holder = (ViewHolder) myView.getTag();
		}
		 
		holder.tvSponserName.setText("");
		holder.tvSponserDetail.setText("");
		
		holder.tvSponserName.setText(""+myArrayList.get(position).getSponsorTitle());
		holder.tvSponserDetail.setText(""+myArrayList.get(position).getSponsorDescription());
		
		holder.ivIcon.setTag(""+myArrayList.get(position).getSponsorLogo());
		imageLoader.DisplayImage(myArrayList.get(position).getSponsorLogo(), activity, holder.ivIcon, IMAGE_SIZE, IMAGE_SIZE);
		
		holder.rlNext.setTag(""+position);
		holder.rlNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				int pos = Integer.parseInt(arg0.getTag().toString());
				Intent intent = new Intent(context,SponsorDetail.class);
				intent.putExtra(GlobalField.SPONSORS, myArrayList.get(pos));
				activity.startActivity(intent);
				activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
			}
		});
		
		return myView; 
	}
	
	class ViewHolder  {
		TextView tvSponserName,tvSponserDetail;
		ImageView ivIcon;
		RelativeLayout rlNext;
	}
}