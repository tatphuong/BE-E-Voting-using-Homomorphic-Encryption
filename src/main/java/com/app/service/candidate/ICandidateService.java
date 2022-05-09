package com.app.service.candidate;

import com.app.models.Candidate;
import com.app.service.IGeneralService;

public interface ICandidateService extends IGeneralService<Candidate> {
    Boolean existsByCitizenIdentity(int citizenId);

}
