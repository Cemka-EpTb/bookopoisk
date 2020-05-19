package com.example.bookopoisk;

public class Book {
    private String name;
    private String author;
    private int imageResourceId;

    public static final Book[] books = {
            new Book("451 градус по Фаренгейту", "Брэдбери Рэй Дуглас", R.drawable.fahrenheit_451),
            new Book("Зеленая миля", "Стивен Кинг", R.drawable.green_mile),
            new Book("Дюна", "Герберт Фрэнк Патрик", R.drawable.dune),
            new Book("Бегущий в лабиринте", "Дешнер Джеймс", R.drawable.maze_runner),
            new Book("Автостопом по Галактике", "Адамс Дуглас Ноэль", R.drawable.guide_to_the_galaxy),
            new Book("Видоизмененный углерод", "Ричард Морган", R.drawable.modified_carbon),
            new Book("Сотня", "Морган Касс", R.drawable.hundred),
            new Book("Игра Эндера", "Кард Орсон Скотт", R.drawable.ender_game),
            new Book("Метро 2034", "Дмитрий Глуховский", R.drawable.metro),
            new Book("Катастрофа", "Тармашев Сергей Сергеевич", R.drawable.catastrophe)
    };

    private Book(String name, String author, int imageResourceId) {
        this.name = name;
        this.author = author;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
