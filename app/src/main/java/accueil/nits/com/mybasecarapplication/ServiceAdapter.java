package accueil.nits.com.mybasecarapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceAdapter extends BaseAdapter {

	private List<Service> listeService;
	private LayoutInflater inflater;
	private Context context;

	public void setCourses(List<Service> listeServices) {
		this.listeService = listeServices;
	}

	public ServiceAdapter(Context context, List<Service> listeService) {
		this.listeService = listeService;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listeService.size();
	}

	@Override
	public Object getItem(int position) {
		return listeService.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listeService.get(position).getId();
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.layout_item_service, null);
			holder.tvp = (TextView) view.findViewById(R.id.textViewprixlistservices);
			holder.tvd = (TextView) view.findViewById(R.id.textViewdatelistservices);
			holder.tvid = (TextView) view.findViewById(R.id.textViewidlistservices);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvid.setText("SERVICE N: "+(position+1)+" ");
		holder.tvp.setText("Type:"+listeService.get(position).gettype() + "  ");
		holder.tvd.setText("Enregistrer Le:"+listeService.get(position).getdate());

		return view;
	}

	private class ViewHolder {
		public TextView tvp;
		public TextView tvd;
		public TextView tvid;
	}
}
