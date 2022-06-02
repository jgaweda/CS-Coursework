import datetime

class Truck:
    '''A truck class that stores and delivers packages'''

    def __init__(self, payload, start_time, truck_num, warehouse):
        '''
        Initializes truck with payload list of packages, the truck number, and a reference to the Hash Table.
        '''   

        # Attributes for the truck class
        self.warehouse = warehouse
        self.address_book = self.warehouse.graph
        self.time = start_time
        self.cargo = payload
        self.number = truck_num
        self.edges = []
        self.miles_traveled = 0.0
        self.current_location = 0
        self.miles_to_next = 0.0
        self.status = 'AT HUB, START OF DAY'

        # Runs the sort_package method to order packages by delivery order, 
        # Also finds and sets the miles to the next package in the list.  
        self.sort()
        self.distance_to_next_stop()

    def __repr__(self):
        '''
        Returns a string for the status of the truck.  
        Includes total packages on truck, miles traveled & time.

        Space-time complexity = O(1)
        '''

        return_string = f'Truck #{self.number} -- {self.status}\n'
        return_string += f'Package count =\t{len(self.cargo)}\n'
        return_string += f'Miles traveled =\t{round(self.miles_traveled, 1)}\n'
    
        if len(self.cargo) == 0 and round(self.miles_traveled, 1) == round(self.miles_to_next, 1):
            return_string += f''
        else: 
            return_string += f'Miles to next = {round(self.miles_to_next, 1)}\n'

        return return_string

    def distance_to_next_stop(self):
        '''
        Finds the miles to the next package and updates miles_to_next.

        Space-time complexity = O(1)
        '''

        # If there are packages left to deliver, find miles traveled from current location to next
        if len(self.cargo) > 0:
            self.miles_to_next += float(self.address_book[int(self.current_location)][int(self.cargo[0].address_id)])

        # Otherwise find miles traveled from current location to hub
        else:
            self.miles_to_next += float(self.address_book[int(self.current_location)][0])

    def deliver(self):
        '''
        Delivers the next package in cargo.  
        Updates the current location, package status in hash table, and miles to next.  

        Space-time complexity = O(1)
        '''
        self.current_location = self.cargo[0].address_id
        self.warehouse.update_package(self.cargo[0], f'DELIVERED at {self.time.time()} on truck #{self.number}')
        self.cargo.pop(0)
        self.distance_to_next_stop()

    def move(self):
        '''
        Moves the truck 0.1 miles and delivers a package if at location.  

        Space-time complexity = O(1)
        '''
        # When there are more packages to deliver, update status and travel towards destination
        if len(self.cargo) > 0:
            self.status = f'Traveling to location {self.cargo[0].address_id}'
            self.travel(0.1)

            # If miles traveled matches miles to next (therefor at destination), deliver package
            while round(self.miles_traveled, 1) == round(self.miles_to_next, 1):
                self.deliver()

        # If there are no more packages and truck not at hub, return to hub
        elif len(self.cargo) == 0 and round(self.miles_traveled, 1) != round(self.miles_to_next, 1):
            self.status = f'Returning to hub'
            self.travel(0.1)
        
        # Truck is at the hub, update status
        else: 
            self.current_location = 0
            self.status = 'Deliveries complete'

    def sort(self):
        '''
        Sorts packages in cargo in order of the shortest path.

        Space-time complexity = O(N^2)  <-- Most complex algorithm used in method is get_dfs_path()
        '''

        # Finds minimal spanning tree, 
        #  Then uses a depth first search to order sort packages
        self.find_minimum_spanning_tree()
        path = self.get_dfs_path()

        # Initializes list for delivery order
        deliveries = []

        # Converts address ID's into packages, adds package to deliveries list
        for address in path:
            deliveries.extend(self.get_packages_from_address(address))

        # Converts cargo to deliveries, which holds all the same packages but now in proper order
        self.cargo = deliveries

    def get_packages_from_address(self, address):
        '''
        Returns a packages from cargo in list form based on address_id

        Space-time complexity = O(N)
        '''

        # Initializes list to store packages to return
        packages = []

        # Iterates through a copy of cargo.
        # If package address matches address id, remove from cargo and add to packages
        for package in self.cargo[:]:
            if int(package.address_id) == int(address):
                packages.append(self.cargo.pop(self.cargo.index(package)))

        # Returns list of packages
        return packages

    def find_minimum_spanning_tree(self):
        '''
        Uses Primm's algorithm to find a minimum spanning tree.  

        Space-time complexity = O(N^2) <-- Due to using with an adjacency matrix.  O(log N) if modified to work with an adjacency list.  
        '''

        # List used for spanning tree path
        addresses = [0]

        # Updates addresses with unique address ID's for packages found in cargo
        for i in self.cargo:
            if int(i.address_id) not in addresses:
                addresses.append(int(i.address_id))

        # Initializes local variables
        num_address = len(addresses)
        num_edges = 0
        selected = [0] * num_address
        selected[0] = True

        # While there are more edges to be found...
        while num_edges < num_address - 1:

            # Set loop variables
            smallest = float('inf')
            fro = 0
            to = 0

            # For each address...
            for i in range(num_address):

                # Checks if that address is currently in list "selected"
                # If the address is in selected, it has been visited.
                if selected[i]:

                    # Checks each address...
                    for j in range(num_address):
                        x = int(addresses[i])
                        y = int(addresses[j])

                        # If address j is not selected and x,y is not equal to zero, 
                        # Then this node in the tree is valid to check if it's the current closest node.  
                        if ( (not selected[j]) and self.address_book[x][y] != 0 ):

                            # If this node is the current closest, update fro/to with addresses and update smallest
                            if float(smallest) > float(self.address_book[x][y]):
                                smallest = float(self.address_book[x][y])
                                fro = x
                                to = y

            # After loop, fro/to will be the smallest remaining edge to address 'to' that hasn't been visited yet.
            # Create and add edge with this info...
            self.edges.append(Edge(fro, to, float(self.address_book[fro][to])))

            # ...add address to selected and increment num_edges
            selected[addresses.index(to)] = True
            num_edges += 1

    def get_dfs_path(self):
        '''
        Implementation of a depth first search for getting the delivery order of packages from minimum spanning tree.  
        This will start at the hub, travel down the minimum spanning tree as far as it can go, then reverse direction 1 space until it can find new nodes.  
        This will add nodes in visitation order to a "visited" list, which will be used for the truck delivery order. 

        Space-time complexity = O(N^2)
        '''
        
        # Initializes visited and unvisited lists & current index
        visited = [0]
        unvisited = []
        current = 0

        # Populates unvisited list with every address ID
        for i in self.cargo:
            if int(i.address_id) not in unvisited:
                unvisited.append(int(i.address_id))
        
        # While there are still unvisited nodes...
        while unvisited:
            found = False

            # ... Check every node from current destingation to see if it unvisited.  
            for edge in self.edges:

                # If the destination of the edge has not been visited, 
                #  add destination to visited, remove from unvisited, 
                #  and update found flag with true.  
                if (int(edge.fro) == current) and (edge.to not in visited):
                    visited.append(edge.to)
                    unvisited.remove(int(edge.to))
                    current = edge.to
                    found = True
            
            # If each edge has been checked and there are no unvisited nodes from current destination, 
            #  "reverse" the travel down the tree and return to a previous visited node.  
            if found == False:
                for edge in self.edges:
                    if int(edge.to) == current:
                        visited.append(int(edge.fro))
                        current = edge.fro
                        break



        # Returns the path with duplicates removed
        return list(dict.fromkeys(visited))

    def num_addresses(self):
        '''
        Find the total number of unique addresses on truck and returns it.
        
        Space-time complexity = O(N)
        '''

        # Initializes list used to hold unique address. 
        unique_addresses = []

        # For each package in cargo, if the address id is not already in list above,
        #  append address ID to list
        for p in self.cargo:
            if p.address_id not in unique_addresses:
                unique_addresses.append(p.address_id)

        # Returns the count of unique address
        return len(unique_addresses)

    def travel(self, miles):
        '''
        Updates current time and miles traveled of truck based on miles driven.
        All trucks within this project drive at a constant 18 MPH,
        or 200 seconds per mile.

        Space-time complexity = O(1)
        '''

        SECONDS_PER_MILE = 200
        driven = datetime.timedelta(0, SECONDS_PER_MILE * miles)
        self.time += driven
        self.miles_traveled += miles



class Edge:
    '''
    Helper class that represents edges in a tree.  
    Used with the Truck class when finding the minimum spanning tree
    '''

    def __init__(self, fro, to, weight):
        self.fro = fro
        self.to = to
        self.weight = weight