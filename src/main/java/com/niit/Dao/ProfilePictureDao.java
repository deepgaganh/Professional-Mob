package com.niit.Dao;

import com.niit.model.ProfilePicture;

public interface ProfilePictureDao {

	void saveProfilePicture(ProfilePicture profilePicture);
	ProfilePicture getProfilePicture(String username);
}
