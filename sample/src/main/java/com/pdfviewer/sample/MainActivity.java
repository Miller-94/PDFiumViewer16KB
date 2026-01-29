package com.pdfviewer.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pdfviewer.sample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.openPdfButton.setOnClickListener(v -> {
            PdfViewerDialogFragment dialog = PdfViewerDialogFragment.newInstance("sample.pdf");
            dialog.show(getSupportFragmentManager(), "pdf_viewer");
        });

        binding.openPdfButton2.setOnClickListener(v -> {
            PdfViewerDialogFragment dialog = PdfViewerDialogFragment.newInstance("problem.pdf");
            dialog.show(getSupportFragmentManager(), "pdf_viewer");
        });
        binding.openPdfButton3.setOnClickListener(v -> {
            PdfViewerDialogFragment dialog = PdfViewerDialogFragment.newInstance("annotation.pdf");
            dialog.show(getSupportFragmentManager(), "pdf_viewer");
        });
    }
}
