package com.pdfviewer.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.pdfviewer.sample.databinding.DialogPdfViewerBinding;

public class PdfViewerDialogFragment extends DialogFragment
        implements OnPageChangeListener, OnLoadCompleteListener, OnErrorListener {

    private static final String ARG_PDF_FILE = "pdf_file";

    private DialogPdfViewerBinding binding;
    private String pdfFileName;
    private int totalPages = 0;

    public static PdfViewerDialogFragment newInstance(String pdfFileName) {
        PdfViewerDialogFragment fragment = new PdfViewerDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PDF_FILE, pdfFileName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);

        if (getArguments() != null) {
            pdfFileName = getArguments().getString(ARG_PDF_FILE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        binding = DialogPdfViewerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();
        loadPdf();
    }

    private void setupToolbar() {
        binding.toolbar.setTitle(pdfFileName);
        binding.toolbar.setNavigationOnClickListener(v -> dismiss());
    }

    private void loadPdf() {
        binding.progressBar.setVisibility(View.VISIBLE);

        binding.pdfView.fromAsset(pdfFileName)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .enableAnnotationRendering(true)  // 체크박스 등 폼 필드 렌더링
                .scrollHandle(new DefaultScrollHandle(requireContext()))
                .spacing(10)
                .onPageChange(this)
                .onLoad(this)
                .onError(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        totalPages = pageCount;
        updatePageInfo(page + 1, pageCount);
    }

    @Override
    public void loadComplete(int nbPages) {
        binding.progressBar.setVisibility(View.GONE);
        totalPages = nbPages;
        updatePageInfo(1, nbPages);
    }

    @Override
    public void onError(Throwable t) {
        binding.progressBar.setVisibility(View.GONE);
        binding.pageInfoText.setText("로드 실패");
    }

    private void updatePageInfo(int currentPage, int totalPages) {
        String pageInfo = getString(R.string.page_info, currentPage, totalPages);
        binding.pageInfoText.setText(pageInfo);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );
            window.setWindowAnimations(android.R.style.Animation_Dialog);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
