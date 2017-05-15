package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.adapter.PhotoAdapter;
import sen.wedding.com.weddingsen.business.utils.RecyclerItemClickListener;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.component.compress.CompressHelper;
import sen.wedding.com.weddingsen.databinding.ContractInfoBinding;

/**
 * Created by lorin on 17/5/2.
 */

public class ContractInfoActivity extends BaseActivity implements View.OnClickListener {

    ContractInfoBinding binding;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contract_info);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.confirm_sign));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        binding.rvPicSelect.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        binding.rvPicSelect.setAdapter(photoAdapter);

//        binding.rvPicSelect.addOnItemTouchListener(new RecyclerItemClickListener(this,
//                new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
//                            PhotoPicker.builder()
//                                    .setPhotoCount(PhotoAdapter.MAX)
//                                    .setShowCamera(true)
//                                    .setPreviewEnabled(false)
//                                    .setSelected(selectedPhotos)
//                                    .start(ContractInfoActivity.this);
//                        } else {
//                            PhotoPreview.builder()
//                                    .setPhotos(selectedPhotos)
//                                    .setCurrentItem(position)
//                                    .start(ContractInfoActivity.this);
//                        }
//                    }
//                }));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();

            File oldFile = new File(selectedPhotos.get(0));
            Glide.with(this)
                    .load(Uri.fromFile(oldFile))
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(binding.imageView1);
            binding.tvOld.setText(String.format("Size : %s", getReadableFileSize(oldFile.length())));


            File newFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(oldFile);
            Glide.with(this)
                    .load(Uri.fromFile(newFile))
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(binding.imageView2);
            binding.tvNew.setText(String.format("Size : %s", getReadableFileSize(newFile.length())));

        }
    }


    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
