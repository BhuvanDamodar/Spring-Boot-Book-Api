package com.book.Book.scheduling;

import com.book.Book.repository.BookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookWatcher {

    private final BookRepo bookRepo;

    @Autowired
    public BookWatcher(final BookRepo bookRepo){
        this.bookRepo=bookRepo;
    }

//    @Scheduled(fixedRate = 5000)  --To execute after 5 seconds interval
//    @Scheduled(cron = "0 15 10 15 * ?" ) -- scheduling a task to be executed at 10:15 AM on the 15th day of every month.
    @Scheduled(cron ="* * * * * *")  // crontab guru - website for cron expression editor
    public void bookCount(){
        final long bookCount = bookRepo.count();
        log.info("There are {} books in the database", bookCount);
    }
}
