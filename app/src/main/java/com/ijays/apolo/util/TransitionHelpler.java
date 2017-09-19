package com.ijays.apolo.util;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ijaysdev on 12/08/2017.
 */

public class TransitionHelpler {

    public static Pair<View, String>[] createSafeTransationParticipants(@NonNull Activity activity,
                                                                        boolean includeStatusBar, Pair... otherParticipants) {

        View decorView = activity.getWindow().getDecorView();
        View statusBar = null;
        if (includeStatusBar) {
            statusBar = decorView.findViewById(android.R.id.statusBarBackground);
        }
        View navBar = decorView.findViewById(android.R.id.navigationBarBackground);

        List<Pair> participants = new ArrayList<>(3);
        addNonNullViewToParticipants(statusBar, participants);
        addNonNullViewToParticipants(navBar, participants);

        if (otherParticipants != null && !(otherParticipants.length == 1 && otherParticipants[0] == null)) {
            participants.addAll(Arrays.asList(otherParticipants));
        }
        return participants.toArray(new Pair[participants.size()]);
    }

    private static void addNonNullViewToParticipants(View view, List<Pair> participants) {
        if (view == null) {
            return;
        }
        participants.add(new Pair<>(view, view.getTransitionName()));
    }
}
