package com.chen.keepsort;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.chen.keepsort.bean.SportGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SportGroupManager {
    private static SportGroupManager instance = new SportGroupManager();

    public static SportGroupManager getInstance() {
        return instance;
    }

    public SportGroup loadGroupByKey(String key) {
        try {
            String data = SP.read(key);
            Logger.d("读取到的数据：" + data);
            return JSON.parseObject(data, SportGroup.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SportGroup loadCurrentGroup() {
        for (SportGroup group : groups) {
            if (TextUtils.equals(group.getKey(), SP.currentSportGroupKey)) {
                return group;
            }
        }
        if (!groups.isEmpty()) {
            return groups.get(0);
        }
        return null;
    }

    private List<SportGroup> groups;
    private List<String> groupKeys;

    private SportGroupManager() {
        initData();
    }

    private void initData() {
        if (groupKeys == null) {
            String dataKeys = SP.read(SP.sportGroupList);
            if (TextUtils.isEmpty(dataKeys)) {
                groupKeys = new ArrayList<>();
            } else {
                groupKeys = JSON.parseArray(dataKeys, String.class);
            }
        }
        if (groups == null) {
            groups = new ArrayList<>();
            for (String key : groupKeys) {
                SportGroup group = loadGroupByKey(key);
                if (group != null) {
                    groups.add(group);
                }
            }
        }
    }

    public List<SportGroup> loadList() {
        return groups;
    }

    public void add(SportGroup group) {
        Logger.d("添加运动组");
        groups.add(group);
        SP.save(group.getKey(), JSON.toJSONString(group));
        groupKeys.add(group.getKey());
        saveKeys();
    }

    public void setDefaultSportGroup(SportGroup group) {
        SP.save(SP.currentSportGroupKey, group.getKey());
    }

    public void delete(SportGroup group) {
        Logger.d("添加运动组");
        SP.save(group.getKey(), "");
        Iterator<SportGroup> it = groups.iterator();
        if (it.hasNext()) {
            SportGroup item = it.next();
            if (TextUtils.equals(group.getKey(), item.getKey())) {
                it.remove();
            }
        }
        Iterator<String> itKeys = groupKeys.iterator();
        if (itKeys.hasNext()) {
            String item = itKeys.next();
            if (TextUtils.equals(group.getKey(), item)) {
                itKeys.remove();
            }
        }
        saveKeys();
    }

    public void saveKeys() {
        String keysData = JSON.toJSONString(groupKeys);
        SP.save(SP.sportGroupList, keysData);
    }
}
