package PaginatorHelperTests;

import com.epam.tkach.carrent.controller.PaginationHelper;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginatorHelperTest {
    @Test
    public void getNoOfPagesTest(){
        int countOfPages = PaginationHelper.getNoOfPages(7,3);
        assertEquals(3, countOfPages);

    }
    @Test
    public void getNoOfPagesTestZeroRecordsPerPage(){
        int countOfPages = PaginationHelper.getNoOfPages(7,0);
        assertEquals(1, countOfPages);
    }
    @Test
    public void getNoOfPagesTestZeroCountOfRecords(){
        int countOfPages = PaginationHelper.getNoOfPages(0,3);
        assertEquals(1, countOfPages);
    }

    @Test
    public void getCurrentPageTest(){

    }
}
