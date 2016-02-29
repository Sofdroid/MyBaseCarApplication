package accueil.nits.com.mybasecarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ConsommationAdapter extends BaseAdapter {

	private List<Consommation> listes;
	private LayoutInflater inflater;
	private Context context;

	public void setconsommation(List<Consommation> liste) {
		this.listes = liste;
	}

	public ConsommationAdapter(Context context, List<Consommation> liste) {
		this.listes = liste;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listes.size();
	}

	@Override
	public Object getItem(int position) {
		return listes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listes.get(position).getId();
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.layout_item_consommation, null);
			holder.tvid = (TextView) view.findViewById(R.id.textViewidlistconso);
			holder.tvd = (TextView) view.findViewById(R.id.textViewdatelistconso);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvid.setText("Consommation N�: "+(position+1)+" ");
		holder.tvd.setText("Enregister Le: "+listes.get(position).getdate());
		return view;
	}
	private class ViewHolder {
		public TextView tvid;
		public TextView tvd;
	}
}
