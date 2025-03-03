package org.serviceexcel.file_service.camunda;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.serviceexcel.file_service.service.Impl.FileServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileCheckDelegate implements JavaDelegate {

    private final FileServiceImpl fileService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String fileName = (String) execution.getVariable("fileName");

        boolean isPdf = fileService.checkFileExtensionPdf(fileName);

        execution.setVariable("isPdf", isPdf);
    }

}