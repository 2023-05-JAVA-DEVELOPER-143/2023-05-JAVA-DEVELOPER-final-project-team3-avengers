package com.danaga.repository;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;

import com.danaga.dao.*;
import com.danaga.entity.*;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

	
}
