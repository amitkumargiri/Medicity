package com.trulloy.bfunx.ui.share;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trulloy.bfunx.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        Intent shareIntent =  new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.trulloy.bfunx";
        String shareSub = "App To Track Medical Expense";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Using: "));
        return root;
    }

}
