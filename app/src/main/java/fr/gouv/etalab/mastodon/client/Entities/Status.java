/* Copyright 2017 Thomas Schneider
 *
 * This file is a part of Mastalab
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * Mastalab is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Mastalab; if not,
 * see <http://www.gnu.org/licenses>. */
package fr.gouv.etalab.mastodon.client.Entities;


import android.app.Activity;
import android.content.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import fr.gouv.etalab.mastodon.R;
import fr.gouv.etalab.mastodon.activities.HashTagActivity;
import fr.gouv.etalab.mastodon.activities.ShowAccountActivity;
import fr.gouv.etalab.mastodon.activities.WebviewActivity;
import fr.gouv.etalab.mastodon.helper.Helper;
import fr.gouv.etalab.mastodon.interfaces.OnRetrieveEmojiInterface;

/**
 * Created by Thomas on 23/04/2017.
 * Manage Status (ie: toots)
 */

public class Status implements Parcelable{

    private String id;
    private String uri;
    private String url;
    private Account account;
    private String in_reply_to_id;
    private String in_reply_to_account_id;
    private Status reblog;
    private Date created_at;
    private int reblogs_count;
    private int favourites_count;
    private boolean reblogged;
    private boolean favourited;
    private boolean muted;
    private boolean pinned;
    private boolean sensitive;
    private boolean bookmarked;
    private String visibility;
    private boolean attachmentShown = false;
    private boolean spoilerShown = false;
    private ArrayList<Attachment> media_attachments;
    private List<Status> replies;
    private List<Mention> mentions;
    private List<Emojis> emojis;
    private List<Tag> tags;
    private Application application;
    private Card card;
    private String language;
    private boolean isTranslated = false;
    private boolean isEmojiFound = false;
    private boolean isEmojiTranslateFound = false;
    private boolean isClickable = false;
    private boolean isTranslationShown = false;
    private boolean isNew = false;
    private boolean isVisible = true;
    private boolean fetchMore = false;
    private Status status;
    private String content, contentCW, contentTranslated;
    private SpannableString contentSpan, contentSpanCW, contentSpanTranslated;


    public Status(){
        this.status = this;
    }

    protected Status(Parcel in) {
        id = in.readString();
        uri = in.readString();
        url = in.readString();
        in_reply_to_id = in.readString();
        in_reply_to_account_id = in.readString();
        reblog = in.readParcelable(Status.class.getClassLoader());
        account = in.readParcelable(Account.class.getClassLoader());
        mentions = in.readArrayList(Mention.class.getClassLoader());
        content = in.readString();
        contentTranslated = in.readString();
        reblogs_count = in.readInt();
        favourites_count = in.readInt();
        reblogged = in.readByte() != 0;
        favourited = in.readByte() != 0;
        muted = in.readByte() != 0;
        sensitive = in.readByte() != 0;
        contentCW = in.readString();
        visibility = in.readString();
        language = in.readString();
        attachmentShown = in.readByte() != 0;
        spoilerShown = in.readByte() != 0;
        isTranslated = in.readByte() != 0;
        isTranslationShown = in.readByte() != 0;
        isNew = in.readByte() != 0;
        pinned = in.readByte() != 0;
    }



    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getIn_reply_to_id() {
        return in_reply_to_id;
    }

    public void setIn_reply_to_id(String in_reply_to_id) {
        this.in_reply_to_id = in_reply_to_id;
    }

    public String getIn_reply_to_account_id() {
        return in_reply_to_account_id;
    }

    public void setIn_reply_to_account_id(String in_reply_to_account_id) {
        this.in_reply_to_account_id = in_reply_to_account_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getReblog() {
        return reblog;
    }

    public void setReblog(Status reblog) {
        this.reblog = reblog;
    }

    public int getReblogs_count() {
        return reblogs_count;
    }

    public void setReblogs_count(int reblogs_count) {
        this.reblogs_count = reblogs_count;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public void setFavourites_count(int favourites_count) {
        this.favourites_count = favourites_count;
    }

    public boolean isReblogged() {
        return reblogged;
    }

    public void setReblogged(boolean reblogged) {
        this.reblogged = reblogged;
    }

    public boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public void setPinned(boolean pinned) { this.pinned = pinned; }

    public boolean isPinned() { return pinned; }

    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public String getSpoiler_text() {
        return contentCW;
    }

    public void setSpoiler_text(String spoiler_text) {
        this.contentCW = spoiler_text;
    }


    public ArrayList<Attachment> getMedia_attachments() {
        return media_attachments;
    }

    public void setMedia_attachments(ArrayList<Attachment> media_attachments) {
        this.media_attachments = media_attachments;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isAttachmentShown() {
        return attachmentShown;
    }

    public void setAttachmentShown(boolean attachmentShown) {
        this.attachmentShown = attachmentShown;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uri);
        dest.writeString(url);
        dest.writeString(in_reply_to_id);
        dest.writeString(in_reply_to_account_id);
        dest.writeParcelable(reblog, flags);
        dest.writeParcelable(account, flags);
        dest.writeList(mentions);
        dest.writeString(content);
        dest.writeString(contentTranslated);
        dest.writeInt(reblogs_count);
        dest.writeInt(favourites_count);
        dest.writeByte((byte) (reblogged ? 1 : 0));
        dest.writeByte((byte) (favourited ? 1 : 0));
        dest.writeByte((byte) (muted ? 1 : 0));
        dest.writeByte((byte) (sensitive ? 1 : 0));
        dest.writeString(contentCW);
        dest.writeString(visibility);
        dest.writeString(language);
        dest.writeByte((byte) (attachmentShown ? 1 : 0));
        dest.writeByte((byte) (spoilerShown ? 1 : 0));
        dest.writeByte((byte) (isTranslated ? 1 : 0));
        dest.writeByte((byte) (isTranslationShown ? 1 : 0));
        dest.writeByte((byte) (isNew ? 1 : 0));
        dest.writeByte((byte) (pinned ? 1 : 0));
    }

    public boolean isSpoilerShown() {
        return spoilerShown;
    }

    public void setSpoilerShown(boolean spoilerShown) {
        this.spoilerShown = spoilerShown;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isTranslated() {
        return isTranslated;
    }

    public void setTranslated(boolean translated) {
        isTranslated = translated;
    }

    public boolean isTranslationShown() {
        return isTranslationShown;
    }

    public void setTranslationShown(boolean translationShown) {
        isTranslationShown = translationShown;
    }

    public String getContentTranslated() {
        return contentTranslated;
    }

    public void setContentTranslated(String content_translated) {
        this.contentTranslated = content_translated;
    }

    public List<Status> getReplies() {
        return replies;
    }

    public void setReplies(List<Status> replies) {
        this.replies = replies;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public List<Emojis> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<Emojis> emojis) {
        this.emojis = emojis;
    }


    public boolean isEmojiFound() {
        return isEmojiFound;
    }

    public void setEmojiFound(boolean emojiFound) {
        isEmojiFound = emojiFound;
    }


    public void makeClickable(Context context){

        if( ((Activity)context).isFinishing() || status == null)
            return;
        SpannableString spannableStringContent, spannableStringCW;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            spannableStringContent = new SpannableString(Html.fromHtml(status.getReblog() != null ?status.getReblog().getContent():status.getContent(), Html.FROM_HTML_MODE_LEGACY));
        else
            //noinspection deprecation
            spannableStringContent = new SpannableString(Html.fromHtml(status.getReblog() != null ?status.getReblog().getContent():status.getContent()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            spannableStringCW = new SpannableString(Html.fromHtml(status.getReblog() != null ?status.getReblog().getSpoiler_text():status.getSpoiler_text(), Html.FROM_HTML_MODE_LEGACY));
        else
            //noinspection deprecation
            spannableStringCW = new SpannableString(Html.fromHtml(status.getReblog() != null ?status.getReblog().getSpoiler_text():status.getSpoiler_text()));

        status.setContentSpan(treatment(context, spannableStringContent));
        status.setContentSpanCW(treatment(context, spannableStringCW));
        isClickable = true;
    }


    public void makeClickableTranslation(Context context){

        if( ((Activity)context).isFinishing() || status == null)
            return;
        SpannableString spannableStringTranslated;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            spannableStringTranslated = new SpannableString(Html.fromHtml(status.getContentTranslated(), Html.FROM_HTML_MODE_LEGACY));
        else
            //noinspection deprecation
            spannableStringTranslated = new SpannableString(Html.fromHtml(status.getContentTranslated()));

        status.setContentSpanTranslated(treatment(context, spannableStringTranslated));
    }



    public void makeEmojis(final Context context, final OnRetrieveEmojiInterface listener){

        if( ((Activity)context).isFinishing() )
            return;
        final List<Emojis> emojis = status.getReblog() != null ? status.getReblog().getEmojis() : status.getEmojis();
        if( emojis != null && emojis.size() > 0 ) {
            final int[] i = {0};
            for (final Emojis emoji : emojis) {
                Glide.with(context)
                        .asBitmap()
                        .load(emoji.getUrl())
                        .listener(new RequestListener<Bitmap>()  {
                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                                i[0]++;
                                if( i[0] ==  (emojis.size())) {
                                    listener.onRetrieveEmoji(status,false);
                                }
                                return false;
                            }
                        })
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                final String targetedEmoji = ":" + emoji.getShortcode() + ":";
                                if (contentSpan.toString().contains(targetedEmoji)) {
                                    //emojis can be used several times so we have to loop
                                    for (int startPosition = -1; (startPosition = contentSpan.toString().indexOf(targetedEmoji, startPosition + 1)) != -1; startPosition++) {
                                        final int endPosition = startPosition + targetedEmoji.length();
                                        contentSpan.setSpan(
                                                new ImageSpan(context,
                                                        Bitmap.createScaledBitmap(resource, (int) Helper.convertDpToPixel(20, context),
                                                                (int) Helper.convertDpToPixel(20, context), false)), startPosition,
                                                endPosition, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                    }
                                }
                                if (contentSpanCW.toString().contains(targetedEmoji)) {
                                    //emojis can be used several times so we have to loop
                                    for (int startPosition = -1; (startPosition = contentSpanCW.toString().indexOf(targetedEmoji, startPosition + 1)) != -1; startPosition++) {
                                        final int endPosition = startPosition + targetedEmoji.length();
                                        contentSpanCW.setSpan(
                                                new ImageSpan(context,
                                                        Bitmap.createScaledBitmap(resource, (int) Helper.convertDpToPixel(20, context),
                                                                (int) Helper.convertDpToPixel(20, context), false)), startPosition,
                                                endPosition, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                    }
                                }
                                i[0]++;
                                if( i[0] ==  (emojis.size())) {
                                    listener.onRetrieveEmoji(status, false);
                                }
                            }
                        });

            }
        }
    }


    public void makeEmojisTranslation(final Context context, final OnRetrieveEmojiInterface listener){

        if( ((Activity)context).isFinishing() )
            return;
        SpannableString spannableStringTranslated = null;

        if( status.getContentTranslated() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                spannableStringTranslated = new SpannableString(Html.fromHtml(status.getContentTranslated(), Html.FROM_HTML_MODE_LEGACY));
            else
                //noinspection deprecation
                spannableStringTranslated = new SpannableString(Html.fromHtml(status.getContentTranslated()));
        }

        final List<Emojis> emojis = status.getReblog() != null ? status.getReblog().getEmojis() : status.getEmojis();
        if( emojis != null && emojis.size() > 0 ) {
            final int[] i = {0};
            for (final Emojis emoji : emojis) {
                final SpannableString finalSpannableStringTranslated = spannableStringTranslated;
                Glide.with(context)
                        .asBitmap()
                        .load(emoji.getUrl())
                        .listener(new RequestListener<Bitmap>()  {
                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                                i[0]++;
                                if( i[0] ==  (emojis.size())) {
                                    if( finalSpannableStringTranslated != null)
                                        status.setContentSpanTranslated(finalSpannableStringTranslated);
                                    listener.onRetrieveEmoji(status, true);
                                }
                                return false;
                            }
                        })
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                final String targetedEmoji = ":" + emoji.getShortcode() + ":";

                                if (finalSpannableStringTranslated != null && finalSpannableStringTranslated.toString().contains(targetedEmoji)) {
                                    //emojis can be used several times so we have to loop
                                    for (int startPosition = -1; (startPosition = finalSpannableStringTranslated.toString().indexOf(targetedEmoji, startPosition + 1)) != -1; startPosition++) {
                                        final int endPosition = startPosition + targetedEmoji.length();
                                        finalSpannableStringTranslated.setSpan(
                                                new ImageSpan(context,
                                                        Bitmap.createScaledBitmap(resource, (int) Helper.convertDpToPixel(20, context),
                                                                (int) Helper.convertDpToPixel(20, context), false)), startPosition,
                                                endPosition, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                    }
                                }
                                i[0]++;
                                if( i[0] ==  (emojis.size())) {
                                    if( finalSpannableStringTranslated != null)
                                        status.setContentSpanTranslated(finalSpannableStringTranslated);
                                    listener.onRetrieveEmoji(status, true);
                                }
                            }
                        });

            }
        }
    }



    private SpannableString treatment(final Context context, final SpannableString spannableString){

        URLSpan[] urls = spannableString.getSpans(0, spannableString.length(), URLSpan.class);
        for(URLSpan span : urls)
            spannableString.removeSpan(span);
        List<Mention> mentions = this.status.getReblog() != null ? this.status.getReblog().getMentions() : this.status.getMentions();
        SharedPreferences sharedpreferences = context.getSharedPreferences(Helper.APP_PREFS, android.content.Context.MODE_PRIVATE);
        int theme = sharedpreferences.getInt(Helper.SET_THEME, Helper.THEME_DARK);

        Matcher matcher;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            matcher = Patterns.WEB_URL.matcher(spannableString);
        else
            matcher = Helper.urlPattern.matcher(spannableString);
        while (matcher.find()){
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            final String url = spannableString.toString().substring(matchStart, matchEnd);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    String finalUrl = url;
                    if( !url.startsWith("http://") && ! url.startsWith("https://"))
                        finalUrl = "http://" + url;
                    Helper.openBrowser(context, finalUrl);
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            },
            matchStart, matchEnd,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, theme==Helper.THEME_DARK?R.color.mastodonC2:R.color.mastodonC4)), matchStart, matchEnd,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        //Deals with mention to make them clickable
        if( mentions != null && mentions.size() > 0 ) {
            //Looping through accounts which are mentioned
            for (final Mention mention : mentions) {
                String targetedAccount = "@" + mention.getUsername();
                if (spannableString.toString().contains(targetedAccount)) {

                    //Accounts can be mentioned several times so we have to loop
                    for(int startPosition = -1 ; (startPosition = spannableString.toString().indexOf(targetedAccount, startPosition + 1)) != -1 ; startPosition++){
                        int endPosition = startPosition + targetedAccount.length();
                        spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View textView) {
                                        Intent intent = new Intent(context, ShowAccountActivity.class);
                                        Bundle b = new Bundle();
                                        b.putString("accountId", mention.getId());
                                        intent.putExtras(b);
                                        context.startActivity(intent);
                                    }
                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        super.updateDrawState(ds);
                                        ds.setUnderlineText(false);
                                    }
                                },
                                startPosition, endPosition,
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, theme==Helper.THEME_DARK?R.color.mastodonC2:R.color.mastodonC4)), startPosition, endPosition,
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }

            }
        }
        matcher = Helper.hashtagPattern.matcher(spannableString);
        while (matcher.find()){
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            final String tag = spannableString.toString().substring(matchStart, matchEnd);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    Intent intent = new Intent(context, HashTagActivity.class);
                    Bundle b = new Bundle();
                    b.putString("tag", tag.substring(1));
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, matchStart, matchEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, theme==Helper.THEME_DARK?R.color.mastodonC2:R.color.mastodonC4)), matchStart, matchEnd,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public SpannableString getContentSpan() {
        return contentSpan;
    }

    public void setContentSpan(SpannableString contentSpan) {
        this.contentSpan = contentSpan;
    }

    public SpannableString getContentSpanCW() {
        return contentSpanCW;
    }

    public void setContentSpanCW(SpannableString contentSpanCW) {
        this.contentSpanCW = contentSpanCW;
    }

    public SpannableString getContentSpanTranslated() {
        return contentSpanTranslated;
    }

    public void setContentSpanTranslated(SpannableString contentSpanTranslated) {
        this.contentSpanTranslated = contentSpanTranslated;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public boolean isEmojiTranslateFound() {
        return isEmojiTranslateFound;
    }

    public void setEmojiTranslateFound(boolean emojiTranslateFound) {
        isEmojiTranslateFound = emojiTranslateFound;
    }

    public boolean isFetchMore() {
        return fetchMore;
    }

    public void setFetchMore(boolean fetchMore) {
        this.fetchMore = fetchMore;
    }



    @Override
    public boolean equals(Object otherStatus) {
        return otherStatus != null && (otherStatus == this || otherStatus instanceof Status && this.getId().equals(((Status) otherStatus).getId()));
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
