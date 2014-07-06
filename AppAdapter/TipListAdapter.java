package com.sportsbetting.AppAdapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sportsbetting.R;
import com.sportsbetting.TipsDetail;
import com.sportsbetting.AppUtil.FontStyle;
import com.sportsbetting.GlobalData.GlobalField;
import com.sportsbetting.Model.TipModel;

public class TipListAdapter extends BaseAdapter {
	private ArrayList<TipModel> myArrayList;
	private Context context;
	private Activity activity;
	private FontStyle fontStyle;
	private String strComeFrom;
	public TipListAdapter(ArrayList<TipModel> myArrayList, Context context,
			Activity activity,String strComeFrom) {
		this.myArrayList = myArrayList;
		this.context = context;
		this.activity = activity;
		this.strComeFrom = strComeFrom;
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

		if (myView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			myView = inflater.inflate(R.layout.style_tip_list, null);
			holder = new ViewHolder();

			holder.tvTitle = (TextView) myView.findViewById(R.id.tvTitle);
			holder.tvTime = (TextView) myView.findViewById(R.id.tvTime);
			holder.tvDetail = (TextView) myView.findViewById(R.id.tvDetail);
			holder.tvPlace = (TextView) myView.findViewById(R.id.tvPlace);
			holder.tvPrice = (TextView) myView.findViewById(R.id.tvPrice);

			fontStyle.setHelveticaRegular(holder.tvTitle);
			fontStyle.setHelveticaRegular(holder.tvTime);
			fontStyle.setHelveticaRegular(holder.tvDetail);
			fontStyle.setHelveticaRegular(holder.tvPlace);
			fontStyle.setHelveticaRegular(holder.tvPrice);

			holder.btnViewDetail = (Button) myView
					.findViewById(R.id.btnViewDetail);

			holder.rlNext = (RelativeLayout) myView.findViewById(R.id.rlNext);
			myView.setTag(holder);
		} else {
			holder = (ViewHolder) myView.getTag();
		}

		holder.tvTitle.setText("");
		holder.tvTime.setText("");
		holder.tvDetail.setText("");
		holder.tvPlace.setText("");
		holder.tvPrice.setText("");

		holder.tvTitle.setText("" + myArrayList.get(position).getMatch());
		holder.tvTime
				.setText("" + myArrayList.get(position).getMatchDatetime());
		holder.tvDetail.setText(""
				+ myArrayList.get(position).getMatchDescription());
		holder.tvPlace.setText("" + myArrayList.get(position).getMatchVenue());
		holder.tvPrice.setText("" + myArrayList.get(position).getTipCoins()
				+ " conins");

		holder.btnViewDetail.setTag("" + position);
		holder.btnViewDetail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				int pos = Integer.parseInt(arg0.getTag().toString());
				Intent intent = new Intent(context, TipsDetail.class);
				intent.putExtra(GlobalField.TIP_TIP, myArrayList.get(pos));
				intent.putExtra(GlobalField.COME_FROM,strComeFrom);
				activity.startActivity(intent);
				activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
			}
		});

		return myView;
	}

	class ViewHolder {
		TextView tvTitle, tvTime, tvDetail, tvPlace, tvPrice;
		Button btnViewDetail;
		RelativeLayout rlNext;
	}
}