package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.group.GroupInput;
import com.svs.hztb.api.sm.model.group.GroupOutput;

public interface GroupDataService {
	GroupOutput removeGroup(GroupInput groupInput);
	GroupOutput createGroup(GroupInput groupInput);
	GroupOutput listGroups(GroupInput groupInput);
}
