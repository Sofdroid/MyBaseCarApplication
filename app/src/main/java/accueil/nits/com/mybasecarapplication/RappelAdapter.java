package accueil.nits.com.mybasecarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class RappelAdapter extends BaseAdapter{
	
	private List<Rappel> liste;
	private LayoutInflater inflater;
	private Context context;
	
	public void setrappel(List<Rappel> liste) {
		this.liste = liste;
	}

	public RappelAdapter(Context context, List<Rappel> liste) {
		this.liste = liste;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return liste.size();
	}

	@Override
	public Object getItem(int position) {
		return liste.get(position);
	}

	@Override
	public long getItemId(int position) {
		return liste.get(position).getId();
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.layout_item_rappel, null);
			holder.tvn = (TextView) view.findViewById(R.id.textViewnomlistrappel);
            holder.tvdate = (TextView) view.findViewById(R.id.textViewdatelistrappel);
            holder.tvid = (TextView) view.findViewById(R.id.textViewidlistrappel);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvid.setText("VOITURE N:  "+(position+1)+" ");
		
		holder.tvn.setText("Nom:  "+liste.get(position).getnom()+" ");
		
	    holder.tvdate.setText("Date: "+liste.get(position).getdate()+" ");
	  
		return view;
	}
	
	private class ViewHolder {
		public TextView tvn;
		public TextView tvdate;
		public TextView tvid;
	}

}
