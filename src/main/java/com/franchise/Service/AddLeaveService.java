package com.franchise.Service;

import java.util.List;
import com.franchise.Entity.AddLeave;

public interface AddLeaveService {

    AddLeave saveLeave(AddLeave addLeave);

    List<AddLeave> getAllLeaves();

    AddLeave updateLeave(Long id, AddLeave updatedLeave);

    AddLeave getLeaveById(Long id);

    void deleteLeaveById(Long id);

    AddLeave updateLeaveStatus(Long id, long status);
}
