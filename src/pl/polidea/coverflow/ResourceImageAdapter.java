package pl.polidea.coverflow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

/**
 * This class is an adapter that provides images from a fixed set of resource
 * ids. Bitmaps and ImageViews are kept as weak references so that they can be
 * cleared by garbage collection when not needed.
 * 
 */
public class ResourceImageAdapter extends AbstractCoverFlowImageAdapter {
    private static final String TAG = ResourceImageAdapter.class
            .getSimpleName();

    private static final int DEFAULT_LIST_SIZE = 20;
    private static final List<Integer> IMAGE_RESOURCE_IDS = new ArrayList<Integer>(
            DEFAULT_LIST_SIZE);
    private static final int[] DEFAULT_RESOURCE_LIST = { R.drawable.image01,
            R.drawable.image02, R.drawable.image03, R.drawable.image04,
            R.drawable.image05 };

    private final HashMap<Integer, WeakReference<Bitmap>> bitmapMap = new HashMap<Integer, WeakReference<Bitmap>>();

    /**
     * Creates the adapter with default set of resource images
     * 
     * @param context
     *            context
     */
    public ResourceImageAdapter(final Context context) {
        super(context);
        setResources(DEFAULT_RESOURCE_LIST);
    }

    /**
     * Replaces resources with those specified.
     * 
     * @param resourceIds
     *            array of ids of resources.
     */
    public synchronized final void setResources(final int resourceIds[]) {
        IMAGE_RESOURCE_IDS.clear();
        for (final int resourceId : resourceIds) {
            IMAGE_RESOURCE_IDS.add(resourceId);
        }
        notifyDataSetChanged();
    }

    @Override
    public synchronized int getCount() {
        return IMAGE_RESOURCE_IDS.size();
    }

    @Override
    protected Bitmap createBitmap(final int position) {
        Log.v(TAG, "creating item " + position);
        final Bitmap bitmap = ((BitmapDrawable) getContext().getResources()
                .getDrawable(IMAGE_RESOURCE_IDS.get(position))).getBitmap();
        bitmapMap.put(position, new WeakReference<Bitmap>(bitmap));
        return bitmap;
    }
}