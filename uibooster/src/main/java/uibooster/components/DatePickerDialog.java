package uibooster.components;

import org.jdesktop.swingx.JXDatePicker;
import uibooster.model.DialogClosingState;

import java.util.Date;

public class DatePickerDialog {

    public static Date showDatePicker(String message, String title, String iconPath) {

        JXDatePicker datePicker = new JXDatePicker(new Date(System.currentTimeMillis()));

        SimpleBlockingDialog dialog = new SimpleBlockingDialog(datePicker);
        DialogClosingState closingState = dialog.showDialog(message, title, iconPath);

        return closingState.isClosedByUser() ? null : datePicker.getDate();
    }

}
