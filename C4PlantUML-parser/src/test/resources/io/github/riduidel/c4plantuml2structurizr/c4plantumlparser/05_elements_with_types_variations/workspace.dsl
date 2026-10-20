workspace "C4_Elements" "" {
	model {
		personAlias = Person "A person" "This is a person"
		systemDBAlias = softwareSystem "A DB system" "This is a system" {
			containerAlias = container "A Queue container" "This is a queue container" {
				technology "Container queue technology"
				componentAlias = component "A component" "This is a component" {
					technology "Component technology"
				}
			}
			containerExtAlias = container "A DB container" "This is a DB container" {
				technology "Container DB technology"
			}
		}
		systemQueueAlias = softwareSystem "An external queue system" "This is a system"
		personAlias -> systemDBAlias "A relationship between person and system" "This is person to system technology"
		personAlias -> containerAlias "A relationship between person and container" "This is person to container technology"
		personAlias -> componentAlias "A relationship between person and component" "This is person to component technology"
	}
	views {
		systemContext systemDBAlias context_of_systemDBAlias "Context view of \"A DB system\"" {
		}
		systemContext systemQueueAlias context_of_systemQueueAlias "Context view of \"An external queue system\"" {
		}
		container systemDBAlias containers_of_systemDBAlias "Container view of \"A DB system\"" {
		}
		component containerAlias components_of_containerAlias "Component view of \"A Queue container\"" {
		}
	}
}