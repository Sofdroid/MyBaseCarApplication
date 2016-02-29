package accueil.nits.com.mybasecarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class VoitureAdapter extends BaseAdapter {

	private List<Voiture> liste;
	private LayoutInflater inflater;
	private Context context;

	public void setvoiture(List<Voiture> liste) {
		this.liste = liste;
	}

	public VoitureAdapter(Context context, List<Voiture> liste) {
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
			view = inflater.inflate(R.layout.layout_item_voiture, null);
			holder.tv1 = (TextView) view.findViewById(R.id.textViewnumvoiture);
			holder.tv2 = (TextView) view.findViewById(R.id.textViewnomvoiture);
            holder.tvmarque = (TextView) view.findViewById(R.id.textViewmarquelistvoiture);
            holder.tvtype = (TextView) view.findViewById(R.id.textViewtypelistvoiture);
            holder.tvid = (TextView) view.findViewById(R.id.textViewidlist);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvid.setText("VOITURE N:  "+(position+1)+" ");
		
		holder.tv1.setText("Num: "+liste.get(position).getnum_mat() +" ");
		
		holder.tv2.setText(" Nom:  "+liste.get(position).getnom()+" ");
		
	    holder.tvmarque.setText("Marque: "+liste.get(position).getmarque()+" ");
	    
	    holder.tvtype.setText("Type de carburant: "+liste.get(position).gettype()+" ");
	    
		return view;
	}
	private class ViewHolder {
		public TextView tv1;
		public TextView tv2;
		public TextView tvmarque;
		public TextView tvtype;
		public TextView tvid;
	}
}