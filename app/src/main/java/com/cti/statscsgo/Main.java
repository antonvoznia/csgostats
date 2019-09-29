package com.cti.statscsgo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cti.statscsgo.ListAdapter.CustomListMainAdapter;
import com.cti.statscsgo.database.BaseNames;
import com.cti.statscsgo.database.Coder;
import com.cti.statscsgo.database.DataBase;
import com.cti.statscsgo.parser.XMLParser;
import com.cti.statscsgo.rating.RatingApp;
import com.cti.statscsgo.update.Update;
import com.cti.statscsgo.variable.StatsConst;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Main extends ActionBarActivity {

    //Firebase Analytics для сбора статистики
    //private FirebaseAnalytics mFirebaseAnalytics;

    /*
    Хранить id в двух переменных, одну для базы данных, мзчененная первая буква, вторая
    хранит для поиска ее в интернете
     */

    private EditText loginSteam;
    private String URL_FIND = "";

    private String URL_STEAM_ID ="steamcommunity.com/id/";


    Intent intent;

    //БД статистики аккаунта стим
    private static DataBase dataBase;
    private SQLiteDatabase db;
    private Context context;
    private String name_data_base, name_coded;

    //Состояние загрузки
    private ProgressBar progressBar;

    //Флаг, который равен true, если идет загрузка, иначе false
    private boolean loadingDo = false;

    private boolean animateDo = false;

    //Диалоговое окно для удаления пользователей из БД и ListView.
    //Значение присваивается в onCreate()
    AlertDialog.Builder dialog;

    //Если показывется окно удаления пользователей, то
    //переменная равна true, иначе false.
    //Используется, чтобы не запускать новую активность в случае
    //долгого нажатия на список пользователей (ListView).
    private boolean dialogIsShowing = false;
    //Позиция user, которого хотим удалить
    private int userPositon;
    ArrayList<String> names_str;

    private CustomListMainAdapter adapter;

    //true, если отсутствует CustomURL (определяется по ID)
    private boolean flag_CODED = false;

    Loader loader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "ID");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "NAME");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/

        context = this;

        TextView site_steam =(TextView) findViewById(R.id.powered_by);
        site_steam.setMovementMethod(LinkMovementMethod.getInstance());
        site_steam.setText(Html.fromHtml(getResources().getString(R.string.steam_site)));
        site_steam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog d = new AlertDialog.Builder(context)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage(Html.fromHtml("<a href=\"https://steamcommunity.com\">Powered by Steam</a>" +
                                "This app used API of Valve and is not a product of Valve."))
                        .create();
                d.show();
                ((TextView) d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        TextView inst = (TextView) findViewById(R.id.instruction_text);
        inst.setMovementMethod(LinkMovementMethod.getInstance());
        inst.setText(Html.fromHtml(getResources().getString(R.string.instruction)));
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("steamcommunity.com/profiles/SteamID\n\n" +
                        "steamcommunity.com/id/CustomURL\n\n" +
                        "Your should use CustomURL or SteamID, or just URL on your profile.\n" +
                        "You can change SteamID in your profile settings.");
                alertDialog.show();
            }
        });

        loginSteam = (EditText) findViewById(R.id.loginSteam);
        loginSteam.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    findLoginSteam();
                    InputMethodManager inputManager =
                            (InputMethodManager) context.
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
        });
        TextView txt = loginSteam;
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoLight.ttf");
        txt.setTypeface(tf);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().
                setColorFilter(Color.WHITE,
                        android.graphics.PorterDuff.Mode.MULTIPLY);

        //loadPref();

        Button findBtn = (Button) findViewById(R.id.findButton);
        txt = (TextView) findBtn;
        txt.setTypeface(tf);
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLoginSteam();
            }
        });
        names_str = new ArrayList<String>();
        adapter = new CustomListMainAdapter(context, names_str);
        setListViewMain();
        RatingApp.showRatingDialog(this);
    }

    private String getNameFromUrl(String str) {
        int index;
        if ( (index = str.lastIndexOf(URL_STEAM_ID )) >-1) {
            str = str.substring(URL_STEAM_ID.length()+index);
            index = str.indexOf("/");
            if (index > -1)
                str = str.substring(0, str.indexOf("/"));
        }
        return str;
    }

    private void findLoginSteam() {
        name_data_base = loginSteam.getText().toString().trim().toLowerCase();
        name_data_base = getNameFromUrl(name_data_base);
        loginSteam.setText(name_data_base);
        if (name_data_base.indexOf(" ") > -1) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage("The ID of steam should not contain spaces.");
            alertDialog.show();
        } else if (name_data_base.length() > 0) {
            name_coded = name_data_base;
            if(setURL()) {
                Coder code = new Coder();
                name_coded = code.toCode(name_coded);
                loginSteam.setText(name_data_base);
            }

            dataBase = new DataBase(context, name_coded);
            db = dataBase.getWritableDatabase();
            if (!dataBase.isTableExist(db, name_coded)) {
                dataBase.addNewTable(db);
            }

            db.close();
            //Устанавливаем видимым ProgressBar на время загрузки данных
            progressBar.setVisibility(View.VISIBLE);
            startLoading();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startLoading() {
        if (!loadingDo && !animateDo) {
            (loader = new Loader()).execute();
        } else {
            Toast.makeText(this, "Loading...", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public class Loader extends AsyncTask <Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            db = dataBase.getWritableDatabase();
            loadingDo = true;
            XMLParser xml_parser = new XMLParser();
            //Log.d("TAG", URL_FIND);
            try {
                String page_stats;
                //`("TAG", URL_FIND);
                if ((page_stats = xml_parser.getDocument(new URL(URL_FIND))) == null) {
                    return 0;
                }
                int result = xml_parser.setStats(page_stats, db, context, name_coded, flag_CODED);
                return result;
            } catch (MalformedURLException e) {
                return 0;
            }
            //return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            db.close();
            if (!result.equals(2)) {
                //Если не смог загрузить статистику
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                if (result.equals(0))
                    alertDialog.setMessage("Error! Problem with INTERNET connection or steam can`t find your ID.");
                else if (result.equals(1)) alertDialog.setMessage("Your steam-accaunt is private!");
                else if (result.equals(4)) {
                    alertDialog.setMessage("The specified profile could not be found.");
                }
                else {
                    db = dataBase.getWritableDatabase();
                    dataBase.deleteTable(db, name_data_base);
                    db.close();
                    alertDialog.setMessage("This user has not played CS GO!");
                }
                alertDialog.setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                loadingDo = false;
                flag_CODED = false;
                progressBar.setVisibility(View.INVISIBLE);
                alertDialog.show();
            } else {
                loadingDo = false;
                flag_CODED = false;
                startNewActivity();
            }
        }

    }

    private void startNewActivity() {
        if (loadingDo) {
            Toast.makeText(this, "Loading...", Toast.LENGTH_LONG)
                    .show();
        } else {
            setDBNames(loginSteam.getText().toString().trim().toLowerCase());
            //setDBNames(name_coded);
            db = dataBase.getReadableDatabase();
            Cursor cursor = db.query(name_coded, null, null, null, null, null, null, null);
            HashMap<String, String> hm = new HashMap<String, String>();
            if (cursor.moveToFirst()) {
                do {
                    hm.put(cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("value")));
                } while (cursor.moveToNext());
            }

            db.close();

            StatsConst cnst = new StatsConst();

            for (String i : cnst.MS_GUNS_KILLS) {
                if (hm.get(i) == null)
                    hm.put(i, "0");
            }
            for (String i : cnst.MS_GUNS_HITS) {
                if (hm.get(i) == null)
                    hm.put(i, "0");
            }
            for (String i : cnst.MS_GUNS_SHOTS) {
                if (hm.get(i) == null)
                    hm.put(i, "0");
            }

            for (String i : cnst.MS_MAPS_WINS) {
                if (hm.get(i) == null) {
                    hm.put(i, "0");
                }
            }

            for (String i : cnst.MS_MAPS_ROUNDS) {
                if (hm.get(i) == null)
                    hm.put(i, "0");
            }

            intent = new Intent(Main.this, StatsActivity.class);
            intent.putExtra("HASH_MAP", hm);
            progressBar.setVisibility(View.INVISIBLE);

            addInArrayList();
            adapter.notifyDataSetChanged();

            startActivity(intent);
        }
    }

    private void setDBNames(String name) {
        BaseNames bn = new BaseNames(this);
        SQLiteDatabase sqlDB = bn.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = sqlDB.rawQuery("SELECT count FROM names_steam WHERE name = ?",
                new String[]{name});
        if (cursor.moveToFirst()) {
            int count = Integer.valueOf(cursor.getString(0));
            cv.put("name", name);
            cv.put("count", ++count);
            sqlDB.update("names_steam", cv, "name = ?", new String[]{name});
        } else {
            cv.put("name", name);
            cv.put("count", 0);
            sqlDB.insert("names_steam", null, cv);
        }
        cursor.close();
        sqlDB.close();
    }

    private void setListViewMain() {
        //Hабота с диалоговым окном, в котором предлагается
        //удалить имеющегося пользователя.
        ListView lv = (ListView) findViewById(R.id.listView_names);

        addInArrayList();

        dialog = new AlertDialog.Builder(this);

        dialog.setCancelable(true).setPositiveButton(
                "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogIsShowing = false;
                        dataBase = new DataBase(context, names_str.get(userPositon));
                        SQLiteDatabase dbMeth = dataBase.getWritableDatabase();
                        BaseNames baseNames2 = new BaseNames(context);
                        SQLiteDatabase db2 = baseNames2.getWritableDatabase();
                        baseNames2.deleteRow(db2, names_str.get(userPositon));
                        db2.close();
                        if (dataBase.isTableExist(dbMeth, names_str.get(userPositon))) {
                            if (flag_CODED) {
                                Coder code = new Coder();
                                dataBase.deleteTable(dbMeth, code.toCode( names_str.get(userPositon)) );
                            } else
                                dataBase.deleteTable(dbMeth, names_str.get(userPositon));
                        }
                        dbMeth.close();
                        dialog.cancel();

                        addInArrayList();
                        adapter.notifyDataSetChanged();
                    }
                }
        ).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogIsShowing = false;
                dialog.cancel();
            }
        });

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!dialogIsShowing) {
                    Animation anim = AnimationUtils.loadAnimation(context,
                            R.anim.to_right);
                    animateDo = true;
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Animation anim2 = AnimationUtils.loadAnimation(context,
                                    R.anim.to_left);
                            anim2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    name_coded = loginSteam.getText().toString();
                                    if (setURL()) {
                                        Coder code = new Coder();
                                        name_coded = code.toCode(name_coded);
                                    }
                                    animateDo = false;
                                    startNewActivity();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            loginSteam.setText(names_str.get(position));
                            loginSteam.startAnimation(anim2);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    loginSteam.startAnimation(anim);
                    name_data_base = names_str.get(position);
                    dataBase = new DataBase(context);

                    name_data_base = names_str.get(position);
                    dataBase = new DataBase(context);
                }
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!loadingDo) {
                    dialogIsShowing = true;
                    dialog.setMessage("Delete " + names_str.get(position) + " ?");
                    userPositon = position;
                    dialog.show();
                }
                return false;
            }
        });
    }

    public void addInArrayList() {
        names_str.removeAll(names_str);
        //Установка списка имен, которые использовались раннее.
        //Хранятся в БД, сортируются по количеству обращений.
        BaseNames baseNames = new BaseNames(context);
        SQLiteDatabase dbNames = baseNames.getReadableDatabase();
        String orderBy =  "count" + " DESC";
        Cursor cursor = dbNames.query("names_steam", null, null, null, null, null, orderBy);

        if (cursor.moveToFirst() ) {
            int i = 0;
            do {
                names_str.add(i++, cursor.getString(cursor.getColumnIndex("name")));
            } while (cursor.moveToNext());
        }
        dbNames.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (loadingDo) {
            loader.cancel(true);
            while(!loader.isCancelled());
            db.close();
            db = dataBase.getWritableDatabase();
            if (dataBase.isTableExist(db, name_coded)) {
                dataBase.deleteTable(db, name_coded);
                db.close();
                BaseNames baseNames = new BaseNames(context);
                db = baseNames.getWritableDatabase();
                baseNames.deleteRow(db, name_data_base);
            }
            db.close();
            loadingDo = false;
            flag_CODED = false;
        }
        /*if (startAnimation) {
            while (startAnimation);
        }*/
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Update update = new Update();
        if (update.loadUpdate(this).equals("1")) {
            update.stopUpdate(this);
            progressBar.setVisibility(View.VISIBLE);
            //URL_FIND = URL_API + loginSteam.getText().toString().toLowerCase() + URL_API_END;
            setURL();
            startLoading();
        }
    }

    private boolean setURL() {
        String str = loginSteam.getText().toString().toLowerCase();
        int length = str.length();
        char ch[] = new char[length];
        str.getChars(0, ch.length, ch, 0);
        int j = 0;
        for(Character c : ch) {
            if(c.isDigit(c))
                ++j;
            else break;
        }
        String URL_API_END = "/?xml=1&l=english";
        if (j == length) {
            URL_FIND = "https://steamcommunity.com/profiles/" + str + URL_API_END;
            flag_CODED = true;
            return true;
        }
        else {
            URL_FIND = "https://steamcommunity.com/id/" + str + URL_API_END;
            flag_CODED = false;
            return false;
        }

    }
}
