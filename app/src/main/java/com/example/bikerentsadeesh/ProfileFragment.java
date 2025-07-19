package com.example.bikerentsadeesh;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private TextView tvEmail, tvUsername, tvActivity;
    private ImageView ivProfileImage;
    private Button btnLogout;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvActivity = view.findViewById(R.id.tvActivity);
        tvActivity.setOnClickListener(v -> openCart());
        tvUsername.setOnClickListener(v -> openEditProfile());
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvActivity = view.findViewById(R.id.tvActivity);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        btnLogout = view.findViewById(R.id.btnLogout);
        dbHelper = new DBHelper(requireContext());

        // Set up logout button click listener
        btnLogout.setOnClickListener(v -> logout());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", requireActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        String email = sharedPreferences.getString("email", "Email");

        if (email != null) {
            byte[] imageData = dbHelper.getUserImageByEmail(email);
            if (imageData != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                ivProfileImage.setImageBitmap(bitmap);
            } else {
                ivProfileImage.setImageResource(R.drawable.animated);
            }
        }

        tvUsername.setText(username);
        tvEmail.setText(email);
    }

    private void logout() {
        // Clear user session data
        Login.clearUserSession(requireContext());

        // Show logout message
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to Login activity
        Intent intent = new Intent(requireActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finish the current activity to prevent back navigation
        requireActivity().finish();
    }

    private void openEditProfile() {
        Intent intent = new Intent(getActivity(), Edit_profile_details.class);
        startActivity(intent);
    }

    private void openCart(){
        Intent intent = new Intent(getActivity(), CartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", requireContext().MODE_PRIVATE);
        String updatedUsername = sharedPreferences.getString("username", "Default Username");

        TextView tvUsername = getView().findViewById(R.id.tvUsername);
        tvUsername.setText(updatedUsername);
    }
}