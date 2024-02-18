package com.example.myapplication1.ui.component.pet_contract

import android.os.Bundle
import com.example.myapplication1.databinding.ActivityPetBinding
import com.example.myapplication1.ui.base.BaseActivity

class PetContractListActivity : BaseActivity() {
    private lateinit var binding: ActivityPetBinding


    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}