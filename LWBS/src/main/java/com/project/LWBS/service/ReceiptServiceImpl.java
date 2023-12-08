package com.project.LWBS.service;

import com.project.LWBS.domain.Receipt;
import com.project.LWBS.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }
}
