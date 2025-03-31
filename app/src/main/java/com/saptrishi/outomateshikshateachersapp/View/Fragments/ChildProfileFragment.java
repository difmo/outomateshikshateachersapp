package com.saptrishi.outomateshikshateachersapp.View.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChildProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView name,   dob,  mname, fmo,  email,father,qualification,pre_add,para_add,blood, textname,textemail;
    Button imageButton;
    CircleImageView profile;
    String profileImageString;
    SharedPreferences sfp;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChildProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildProfileFragment newInstance(String param1, String param2) {
        ChildProfileFragment fragment = new ChildProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_teacherprofile, container, false);

        imageButton = view.findViewById(R.id.backButton);
        name = view.findViewById(R.id.tvSVchildName);

        dob = view.findViewById(R.id.tvSVchildDOB);
         textname=view.findViewById(R.id.tvSVchildNames);
//        mname = view.findViewById(R.id.tvSVchildMotherName);
        fmo = view.findViewById(R.id.tvSVchildFatherMno);
        email = view.findViewById(R.id.tvSVchildEmailAdd);
        textemail=view.findViewById(R.id.tvSVchildEmailAdds);
//        pre_add = view.findViewById(R.id.tv_tech_address);
        para_add = view.findViewById(R.id.tv_tech_parmanentaddress);
//        blood = view.findViewById(R.id.tv_tech_bloodgroup);
//        qualification = view.findViewById(R.id.tv_tech_qualification);
        father = view.findViewById(R.id.fathersdetails);
//        profile = view.findViewById(R.id.iv_imageview);

        sfp = getContext().getSharedPreferences("Teacher", MODE_PRIVATE);

        profileImageString=sfp.getString("profileImageString","");
        byte[] decodedString = Base64.decode(profileImageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        if (profileImageString.equals(""))
//        {
//            profile.setImageResource(R.drawable.user_image);
//
//        }
//        else
//        {
//            profile.setImageBitmap(decodedByte);
//        }


        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        final String staffclientid = sharedPreferences.getString("childrenid", "");
        MySqliteDataBase mySqliteDataBase = new MySqliteDataBase(getActivity().getApplicationContext());
        Cursor cursor = mySqliteDataBase.fetchMasterTable();
        if (cursor.moveToFirst()) {
            Log.e("aa1",cursor.getString(1));
            Log.e("aa2",cursor.getString(2));
            Log.e("aa3",cursor.getString(3));
            Log.e("aa4",cursor.getString(4));
            Log.e("aa5",cursor.getString(5));
            Log.e("aa6",cursor.getString(6));
            Log.e("aa7",cursor.getString(7));
            Log.e("aa8",cursor.getString(8));
            Log.e("aa9",cursor.getString(9));
            Log.e("aa10",cursor.getString(10));
            Log.e("aa10",cursor.getString(11));
            Log.e("aa11",cursor.getString(12));
            Log.e("aa12",cursor.getString(13));
            Log.e("aa13",cursor.getString(14));
            Log.e("aa14",cursor.getString(15));
            Log.e("aa15",cursor.getString(16));
            Log.e("aa16",cursor.getString(17));
            Log.e("aa17",cursor.getString(18));
            textname.setText(cursor.getString(11));
            name.setText(cursor.getString(11));
            dob.setText(cursor.getString(8));
//            mname.setText(cursor.getString(7));
             father.setText(cursor.getString(13));
          textemail.setText(cursor.getString(9));
            fmo.setText(cursor.getString(12));
//            qualification.setText(cursor.getString(14));
//            pre_add.setText(cursor.getString(15));
            para_add.setText(cursor.getString(16));
//            blood.setText(cursor.getString(17));
            email.setText(cursor.getString(9));
//            fmo.setText(cursor.getString(12));
//            fmo.setText(cursor.getString(12));
//            Picasso.get().load(cursor.getString(18)).error(R.drawable.profile).placeholder(R.drawable.profile).into(profile);
//            father.setText(cursor.getString(12));
//            fmo.setText(cursor.getString(12));
//            email.setText("NA");
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("No Data Kindly Try again later...");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainMenuActivity.class));
                getActivity().overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}