package com.example.shanghai.daojishiapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.shanghai.daojishiapplication.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shanghai on 2018/4/3.
 */

public class Sample5Adapter extends SampleAdapter<Sample5Adapter.ViewHolder5> {
    private Context mContext;
    // 定时器数组，每个 item 都需要一个定时器
    private Disposable disposables[] = new Disposable[100];
    // 是否更新 ui 的开关
    private boolean flags[] = new boolean[100];
    // 用于记录 item 中 progressBar 的进度
    private int progresss[] = new int[100];

    public Sample5Adapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder5 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder5(LayoutInflater.from(mContext).inflate(R.layout.item_sample_2, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder5 holder, int position) {
      /*  holder?.textView?.text = position.toString()
        holder?.progressBar?.progress = progresss[position]
        when {
            disposables[position] == null -> holder?.button?.text = "start"
            else -> holder?.button?.text = "stop"
        }
        // 关闭上一次位置的开关
        if (holder?.lastPosition != -1) {
            flags[holder?.lastPosition!!] = false
        }
        // 开启当前位置的开关
        flags[position] = true
        holder.lastPosition = position*/
        holder.textView.setText(position+"");
        holder.progressBar.setProgress(progresss[position]);
        holder.button.setText(disposables[position] == null ? "start" : "stop");
        if (holder.lastPosition != -1) {
            flags[holder.lastPosition] = false;
        }
        flags[position] = true;
        holder.lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class ViewHolder5 extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        Button button;
        TextView textView;
        int lastPosition = -1;

        public ViewHolder5(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            button = (Button) itemView.findViewById(R.id.button);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            button.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                if (disposables[position] == null) {
                    /*disposables[position] = Observable.interval(0, 1, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.computation())
                            .filter { it <= 100 && flags[position] }
                            .map { it.toInt() }
                            .doOnNext { progresss[position] = it }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { progressBar?.progress = it }
                    addDisposable(disposables[position])
                    button.text = "stop"*/
                    disposables[position] = Observable.interval(0, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.computation())
                            .filter(aLong -> {
                                if (aLong <= 100 && flags[position]) {
                                    Log.e("flag","true"+position);
                                    return true;
                                } else {
                                    Log.e("flag","false"+position);
                                    if(aLong>100){
                                        Log.e("flag","false超过100了");
                                        disposables[position].dispose();
                                    }
                                    return false;
                                }
                            }).map((Function<Long, Integer>) aLong -> aLong.intValue()).doOnNext(integer -> {
                                progresss[position] = integer;
                            }).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
                                progressBar.setProgress(integer);
                                textView.setText(integer+"%");
                            });
                    addDisposable(disposables[position]);
                    button.setText("stop");
                } else {
                   /* disposables[position]?.dispose()
                    removeDisposable(disposables[position])
                    disposables[position] = null
                    progresss[position] = 0
                    progressBar?.progress = 0
                    button.text = "start"*/
                    disposables[position].dispose();
                    removeDisposable(disposables[position]);
                    disposables[position] = null;
                    progresss[position] = 0;
                    progressBar.setProgress(0);
                    button.setText("start");

                }

            });

        }
    }
}
