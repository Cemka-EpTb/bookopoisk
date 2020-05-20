package com.example.bookopoisk;

class ReviewsTemp {
    private String name;
    private int rating;
    private String review;
    private int imageResourceId;

    static private String tempName = "Имя пользователя";
    static private int tempMark = 7;
    static private String tempReview = "В этой книге экономист из Кембриджа Ха-Джун Чанг в занимательной и доступной форме объясняет, как на самом деле работает мировая экономика. Автор предлагает читателю идеи, которых не найдешь в учебниках по экономике, и делает это с глубоким знанием истории, остроумием и легким пренебрежением к традиционным экономическим концепциям.Книга будет полезной для тех, кто интересуется экономикой и хочет лучше понимать, как устроен мир.";

    static final ReviewsTemp[] reviews = {
            new ReviewsTemp(tempName, tempMark, tempReview, R.mipmap.avatar_default),
            new ReviewsTemp(tempName, tempMark, tempReview, R.mipmap.avatar_default),
            new ReviewsTemp(tempName, tempMark, tempReview, R.mipmap.avatar_default),
    };

    private ReviewsTemp(String name, int rating, String review, int imageResourceId) {
        this.name = name;
        this.rating = rating;
        this.review = review;
        this.imageResourceId = imageResourceId;
    }

    String getName() {
        return name;
    }

    String getReview() {
        return review;
    }

    int getRating() {
        return rating;
    }

    int getImageResourceId() {
        return imageResourceId;
    }
}
