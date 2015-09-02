package com.netease.nim.demo.team.helper;

import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.contact.core.item.ContactIdFilter;
import com.netease.nim.demo.contact.protocol.ContactHttpClient;
import com.netease.nim.demo.contact_selector.activity.ContactSelectActivity;
import com.netease.nimlib.sdk.team.constant.TeamMemberType;
import com.netease.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.netease.nimlib.sdk.team.model.TeamMember;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzxuwen on 2015/3/25.
 */
public class TeamHelper {
    public static VerifyTypeEnum getVerifyTypeEnum(String name) {
        VerifyTypeEnum type = null;

        if (name.equals(DemoCache.getContext().getString(R.string.team_allow_anyone_join))) {
            type = VerifyTypeEnum.Free;
        } else if (name.equals(DemoCache.getContext().getString(R.string.team_need_authentication))) {
            type = VerifyTypeEnum.Apply;
        } else if (name.equals(DemoCache.getContext().getString(R.string.team_not_allow_anyone_join))) {
            type = VerifyTypeEnum.Private;
        }

        return type;
    }

    public static List<String> createAuthenMenuStrings() {
        int[] res = new int[]{R.string.team_allow_anyone_join, R.string.team_need_authentication,
                R.string.team_not_allow_anyone_join, R.string.cancel};
        List<String> btnNames = new ArrayList<>();
        for (int r : res) {
            btnNames.add(DemoCache.getContext().getString(r));
        }
        return btnNames;
    }

    public static int getVerifyString(VerifyTypeEnum type) {
        if (type == VerifyTypeEnum.Free) {
            return R.string.team_allow_anyone_join;
        } else if (type == VerifyTypeEnum.Apply) {
            return R.string.team_need_authentication;
        } else {
            return R.string.team_not_allow_anyone_join;
        }
    }

    /**
     * 获取通讯录选择器option
     *
     * @param memberAccounts
     * @return
     */
    public static ContactSelectActivity.Option getContactSelectOption(List<String> memberAccounts) {
        ContactSelectActivity.Option option = new ContactSelectActivity.Option();
        option.title = DemoCache.getContext().getString(R.string.invite_member);
        ArrayList<String> disableUids = new ArrayList<>();
        disableUids.addAll(memberAccounts);
        option.itemDisableFilter = new ContactIdFilter(disableUids);
        return option;
    }

    private static Map<TeamMemberType, Integer> teamMemberLevelMap = new HashMap<>(4);

    static {
        teamMemberLevelMap.put(TeamMemberType.Owner, 0);
        teamMemberLevelMap.put(TeamMemberType.Manager, 1);
        teamMemberLevelMap.put(TeamMemberType.Normal, 2);
        teamMemberLevelMap.put(TeamMemberType.Apply, 3);
    }

    public static Comparator<TeamMember> teamMemberComparator = new Comparator<TeamMember>() {
        @Override
        public int compare(TeamMember l, TeamMember r) {
            if(l == null) {
                return 1;
            }

            if(r == null) {
                return -1;
            }

            return teamMemberLevelMap.get(l.getType()) - teamMemberLevelMap.get(r.getType());
        }
    };
}