package org.openmrs.module.accounting.web.controller.budget;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.openmrs.module.accounting.api.model.Budget;
import org.openmrs.module.accounting.api.model.BudgetItem;


public class BudgetCommand {
		private Budget budget;

		@SuppressWarnings("unchecked")
        private List<BudgetItem> budgetItems = LazyList.decorate(
			new ArrayList<BudgetItem>(),FactoryUtils.instantiateFactory(BudgetItem.class));

		public BudgetCommand(){
			budget = new Budget();
		}
		
		
        public Budget getBudget() {
        	return budget;
        }

		
        public void setBudget(Budget budget) {
        	this.budget = budget;
        }


		
        public List<BudgetItem> getBudgetItems() {
        	return budgetItems;
        }


		
        public void setBudgetItems(List<BudgetItem> budgetItems) {
        	this.budgetItems = budgetItems;
        }

		
		
		
		
		
}
