package com.clouway.bank.utils;

import com.clouway.bank.core.Transaction;
import com.clouway.bank.core.TransactionRepository;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Pager {
  private TransactionRepository transactionRepository;
  private int pageSize;
  private final String email;

  @Inject
  public Pager(TransactionRepository transactionRepository, @Named("pageSize") int pageSize, String email) {
    this.email = email;
    this.transactionRepository = transactionRepository;
    this.pageSize = pageSize;
  }

  public List<Transaction> getPage(int currentPageNumber) {
    return transactionRepository.getTransactions(email, pageSize, (currentPageNumber - 1) * pageSize);
  }

  public int getNumberOfPages() {
    Integer totalNumberOfRecords = transactionRepository.getNumberOfRecords();
    if (totalNumberOfRecords % pageSize != 0) {
      return ((totalNumberOfRecords / pageSize) + 1);

    } else {
      return totalNumberOfRecords / pageSize;
    }
  }
}