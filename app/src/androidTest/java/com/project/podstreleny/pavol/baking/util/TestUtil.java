package com.project.podstreleny.pavol.baking.util;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;

public class TestUtil {

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher){
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View,RecyclerView>(RecyclerView.class) {
            @Override
            protected boolean matchesSafely(RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if(viewHolder == null){
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }

            /**
             * Generates a description of the object.  The description may be part of a
             * a description of a larger object of which this is just a component, so it
             * should be worded appropriately.
             *
             * @param description The description to be built or appended to.
             */
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position: " + position +":");
                itemMatcher.describeTo(description);
            }
        };
    }

}
