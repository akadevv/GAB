package com.example.lcd.gab.Pay_room;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.lcd.gab.R;

import java.util.List;

/**
 * Created by LCD on 2016-01-11.
 */
public class RoomMain extends Activity {
    static String logstring = "jjunest";
    Context makingContext;
    boolean cheese_check;
    boolean meat_check;
    CheckBox cheeseCheck;
    CheckBox meatCheck;
    java.util.List<java.util.Map.Entry<String, Integer>> itempairList = new java.util.ArrayList<>();
    String newDRoomName;
    String newDRoomDate;
    String newDRoomTotalPrice;
    List<EditText> party_edittexts;
    java.util.List<java.util.Map.Entry<String, Integer>> participant_pairList = new java.util.ArrayList<>();

    //for_datepicker
    TextView dateText;
    Button datepickButton;
    int year_x, month_x, day_x;
    static final int DILOG_ID = 0;
    //for_drag&drop
    private static final String IMAGEVIEW_TAG = "The Android Logo";
    ImageView beer_img;
    ImageView chicken_img;
    ImageView hambuger_img;
    //for_ drag&drop item into DATA
    List<ImageView> selectedLists = new ArrayList<ImageView>();
    LinearLayout itemselectedcontainView;
    LinearLayout itemselectercontainView;

//    //for_drag&drop
//    private android.widget.RelativeLayout.LayoutParams layoutParams;
//    ImageView beer_img;
//    String msg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_main);

        makingContext = getApplicationContext();
        final LinearLayout roommakingLayout = (LinearLayout) findViewById(R.id.participants_layout);

        //get new roominfo_name
        EditText newDRoomNameedit = (EditText) findViewById(R.id.edittext2);
        newDRoomName = newDRoomNameedit.getText().toString();

        //get new roominfo_date
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        dateText = (TextView) findViewById(R.id.date_display);
        dateText.setText("0000-00-00");
        datepickButton = (Button) findViewById(R.id.pickDate);
        datepickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("byjjunest _ OnClick() in datepicker");
                showDialog(DILOG_ID);

            }
        });

        //image drag & drop selector
        beer_img = (ImageView) findViewById(R.id.beerimg);
        beer_img.setTag(IMAGEVIEW_TAG);
        beer_img.setOnLongClickListener(new MyClickListener());
        chicken_img=(ImageView)findViewById(R.id.chickenimg);
        chicken_img.setTag(IMAGEVIEW_TAG);
        chicken_img.setOnLongClickListener(new MyClickListener());
        hambuger_img=(ImageView)findViewById(R.id.hambuger);
        hambuger_img.setTag(IMAGEVIEW_TAG);
        hambuger_img.setOnLongClickListener(new MyClickListener());

        findViewById(R.id.selectLayout).setOnDragListener(new MyDragListener());
        findViewById(R.id.selectedLayout).setOnDragListener(new MyDragListener());

        //insert drag&drop item into data;



        cheeseCheck = (CheckBox) findViewById(R.id.checkbox_cheese);
        cheese_check = false;
        cheeseCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheese_check = cheeseCheck.isChecked();
                if (cheese_check) {
                    Toast.makeText(getApplicationContext(), "this is checked", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "this is unchecked", Toast.LENGTH_LONG).show();
                }
            }
        });

        meatCheck = (CheckBox) findViewById(R.id.checkbox_meat);
        meat_check = false;
        meatCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meat_check = meatCheck.isChecked();
                if (meat_check) {
                    Toast.makeText(getApplicationContext(), "meat is checked", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "meat is unchecked", Toast.LENGTH_LONG).show();
                }
            }
        });

        EditText participants = (EditText) findViewById(R.id.edittext6);
        participants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("byjjunest in afterTextChanged");
                EditText editText = new EditText(makingContext);
                editText.setText("dddddddddddddddddddddddddddddddd");
                editText.setTextSize(30);
                roommakingLayout.addView(editText);

            }
        });

    }


    private final class MyClickListener implements View.OnLongClickListener {

                // called when the item is long-clicked

        @Override

        public boolean onLongClick(View view) {

            // TODO Auto-generated method stub

            // create it from the object's tag

            ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());
            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, //data to be dragged
                    shadowBuilder, //drag shadow
                    view, //local data about the drag and drop operation
                    0   //no needed flags
            );

            view.setVisibility(View.VISIBLE);
            Log.d(logstring, "onLongClickListener on longClick : view : " + view);
            Log.d(logstring, "onLongClickListener on longClick : data : "+data);
            return true;
        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable normalShape = getResources().getDrawable(R.drawable.normal_shape);
        Drawable targetShape = getResources().getDrawable(R.drawable.target_shape);
        public void checkItems (List<ImageView> checklists){
            int counter = checklists.size();
              Log.d(logstring, "checkitems() started");
            for(int i=0; i<counter; i++){
                Log.d(logstring, "image view's id is :" + checklists.get(i).getId());

            }
        }
        //for insert item into data
        public void insertItemsintoData(LinearLayout selectedLayout){
            Log.d(logstring, "insertItemsIntoDATA() :" + selectedLayout);
            int count = selectedLayout.getChildCount();
            Log.d(logstring, "insertItemsIntoDATA() count before clear " + count);
            int countesr =selectedLists.size();
            selectedLists.clear();
//            for(int i=0 ; i <countesr ;i++){
//                Log.d(logstring, "selectedList: ");
//                Log.d(logstring, "counter before clear ;get(i); "+  selectedLists.get(i));
//                Log.d(logstring, "counter before clear ;get(i); " + selectedLists.get(i).getId());
//            }
//             if(selectedLists.isEmpty()){
//                 Log.d(logstring, "is empty() "+ count);
//             }
//            Log.d(logstring, "insertItemsIntoDATA() count " + count);

            for (int i = 0; i < count; i++) {
                View view = selectedLayout.getChildAt(i);
                Log.d(logstring, "child.view() view :"+view);
                if (view.getDrawableState()==null){
                    Log.d(logstring, "getDrableState() is null : ");
                }

                else {
                    selectedLists.add((ImageView)view);
                    Log.d(logstring, "else getDrawableState() is not null : ");
                }
            }
        }
        public boolean onDrag(View v, DragEvent event) { //v 는 drop 이 위치한 view를 의미한다

            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    System.out.println("byjjunest this is DragStarted");
                    Log.d(logstring, "instarted");
                    // do nothing
                    break;
                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                    System.out.println("byjjunest this is Dragentered");
                    Log.d(logstring, "inentered");
                 //   v.setBackgroundDrawable(targetShape);   //change the shape of the view
                    break;
                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    System.out.println("byjjunest this is DRAGEXITED");
                  //  v.setBackgroundDrawable(normalShape);   //change the shape of the view back to normal
                    break;
                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    // if the view is the bottomlinear, we accept the drag item
                    Log.d(logstring,"Action_DROP : v :"+v);
                    if(v == findViewById(R.id.selectedLayout)) { //v 는 drop 이 된 view를 의미한다.
                        Log.d(logstring, "action drop : in selected layout");
                        View view = (View) event.getLocalState(); //drag해서 선택된 view를 의미한다 (image를 의미함)
                        Log.d(logstring, "action drop : view : " + view);
                        ViewGroup viewgroup = (ViewGroup) view.getParent(); //view group 은 그 선택된 뷰의 원래 부모그룹을 의미한다.
                        Log.d(logstring, "action drop : viewGroup : " + viewgroup);
                        ImageView addedView =(ImageView)view;

                        Log.d(logstring, "this is added view : " + addedView);
                        Log.d(logstring,"this is view :"+view);
                          viewgroup.removeView(addedView);
                        itemselectedcontainView = (LinearLayout)v;
                        itemselectedcontainView.addView(addedView);
                        itemselectedcontainView.setBackgroundColor(Color.parseColor("#EC1A1A"));
                        Log.d(logstring, "action after addview : " + addedView);
                        view.setVisibility(addedView.VISIBLE);
                        //change the text

                    } else if(v == findViewById(R.id.selectLayout))
                     {
                    Log.d(logstring, "action drop selectLayout : in select layout : "+v);
                    View view = (View) event.getLocalState(); //drag해서 선택된 view를 의미한다 (image를 의미함)
                    Log.d(logstring, "action drop : view : in select Layout " + view);
                    ViewGroup viewgroup = (ViewGroup) view.getParent(); //view group 은 그 선택된 뷰의 원래 부모그룹을 의미한다.
                    Log.d(logstring, "action drop : viewGroup : in select Layout" + viewgroup);
                    ImageView addedView = (ImageView)view;
                    viewgroup.removeView(view);
                         itemselectercontainView = (LinearLayout)v;
                         itemselectercontainView.addView(addedView);

                         itemselectercontainView.setBackgroundColor(Color.parseColor("#17F60F"));
                    Log.d(logstring, "action after addview : " + addedView);
                    view.setVisibility(addedView.VISIBLE);
                        break;
                    }
                    else {

                    }
                    break;

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(logstring, "action Drag_ENDED");
                    insertItemsintoData(itemselectedcontainView);
                    checkItems(selectedLists);
                   // v.setBackgroundDrawable(normalShape);   //go back to normal shape
                default:
                    break;
            }
            return true;
        }
    }



    //for calendar_dialog
    protected Dialog onCreateDialog(int id) {
        System.out.println("byjjunest _ OncreateDialog");
        if (id == DILOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }

    public DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            if (month_x < 10) {
                dateText.setText(String.valueOf(year_x).toString() + "0" + String.valueOf(month_x).toString() + String.valueOf(day_x).toString());
            } else {
                dateText.setText(String.valueOf(year_x).toString() + String.valueOf(month_x).toString() + String.valueOf(day_x).toString());
            }
        }
    };

    //for drag&drop selector
    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics (Point size, Point touch){
        // Defines local variables
        int width, height;

        // Sets the width of the shadow to half the width of the original View
        width = getView().getWidth() / 2;

        // Sets the height of the shadow to half the height of the original View
        height = getView().getHeight() / 2;

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
        // Canvas that the system will provide. As a result, the drag shadow will fill the
        // Canvas.
        shadow.setBounds(0, 0, width, height);

        // Sets the size parameter's width and height values. These get back to the system
        // through the size parameter.
        size.set(width, height);

        // Sets the touch point's position to be in the middle of the drag shadow
        touch.set(width / 2, height / 2);
    }

    // Defines a callback that draws the drag shadow in a Canvas that the system constructs
    // from the dimensions passed in onProvideShadowMetrics().
    @Override
    public void onDrawShadow(Canvas canvas) {

        // Draws the ColorDrawable in the Canvas passed in from the system.
        shadow.draw(canvas);
    }


}
}
