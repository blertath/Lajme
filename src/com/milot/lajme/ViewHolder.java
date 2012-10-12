package com.milot.lajme;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	TextView txtTitle;
	TextView txtDate;
	ImageView thumbnailImage;
	
	View base;
	
	public ViewHolder(View base) {
		this.base = base;
	}
	
	
	public TextView getTitleView() {
		if(txtTitle == null) {
			txtTitle = (TextView) base.findViewById(R.id.txtTitle);
		}
		
		return txtTitle;
	}
	
}
